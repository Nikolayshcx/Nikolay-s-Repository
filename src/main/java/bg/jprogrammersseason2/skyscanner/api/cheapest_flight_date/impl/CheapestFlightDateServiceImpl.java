package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl;

import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.CheapestFlightDateService;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.CheapestQuoteDto;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.LocationsDto;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.QuotesByPriceDto;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data.ReceivedFlightData;
import bg.jprogrammersseason2.skyscanner.api.statistics.SavedDestinationSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Service
public class CheapestFlightDateServiceImpl implements CheapestFlightDateService
{
  private final SavedDestinationSearchService savedDestinationSearchService;
  private final String                        BROWSE_DATES_TEMPLATE_URL;
  private final String                        BROWSE_PLACES_TEMPLATE_URL;
  private final String                        API_KEY;
  private final ObjectMapper                  objectMapper;
  private final WebClient                     webClient;
  private final Logger                        log;
  String BROWSE_QUOTES_URL_TEMPLATE;

  public CheapestFlightDateServiceImpl(SavedDestinationSearchService savedDestinationSearchService,
                                       @Value("${skyscanner.browse-dates-template-url}") String BROWSE_DATES_TEMPLATE_URL,
                                       @Value("${skyscanner.browse-places-template-url}") String BROWSE_PLACES_TEMPLATE_URL,
                                       @Value("${skyscanner.api-key}") String API_KEY,
                                       @Value("${skyscanner.browse-quotes-template-url}") String BROWSE_QUOTES_URL_TEMPLATE,
                                       @Qualifier("objectMapper") ObjectMapper objectMapper,
                                       @Qualifier("webClient") WebClient webClient,
                                       @Qualifier("logger") Logger log)
  {
    this.BROWSE_QUOTES_URL_TEMPLATE = BROWSE_QUOTES_URL_TEMPLATE;
    this.savedDestinationSearchService = savedDestinationSearchService;
    this.BROWSE_DATES_TEMPLATE_URL = BROWSE_DATES_TEMPLATE_URL;
    this.BROWSE_PLACES_TEMPLATE_URL = BROWSE_PLACES_TEMPLATE_URL;
    this.API_KEY = API_KEY;
    this.objectMapper = objectMapper;
    this.webClient = webClient;
    this.log = log;
  }

  @Override
  public Mono<CheapestQuoteDto> searchByLocation(String destinationPlace) throws ExecutionException, InterruptedException, JsonProcessingException
  {
    // construct initial date
    final int numberOfMonths = 3;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    Calendar c = Calendar.getInstance();

    final String originPlace = "SOF";

    // construct numberOfMonths dates
    String[] outboundPartialDate = new String[numberOfMonths];
    for (int i = 0; i < numberOfMonths; i++) {
      outboundPartialDate[i] = dateFormat.format(c.getTime());
      c.add(Calendar.MONTH, 1);
    }
    log.info(Arrays.toString(outboundPartialDate));

    // collect responses into a list
    ArrayList<QuotesByPriceDto> quotesByPriceDtoList = new ArrayList<>();

    // send requests
    for (int i = 0; i < numberOfMonths; i++) {
      QuotesByPriceDto quotesByPriceDto = webClient.get()
//          .uri(BROWSE_DATES_TEMPLATE_URL, originPlace, destinationPlace, outboundPartialDate[i], API_KEY)
          .uri("/browsedates/v1.0/BG/BGN/bg-BG/SOF-sky/BOJ-sky/2021-10?apikey=prtl6749387986743898559646983194")
          .retrieve()
          .bodyToMono(QuotesByPriceDto.class)
          .timeout(Duration.ofSeconds(5))
//          .log("reactor.netty", Level.FINE)
          .toFuture()
          .get();

      if (!quotesByPriceDto.getQuotes().isEmpty()) {
        quotesByPriceDtoList.add(quotesByPriceDto);
      }

    }

    // TODO: if there are no results from SkyScanner do I still count the search?
    // Reneta : yes
    // but what do I count ?
    // the search string may be invalid, should i count that too?
    // there should be a check in their database unrelated to my search
    // but still how can i be sure whether any item from their database is really what i want to count?

    if (quotesByPriceDtoList.size() < 1) {
      return Mono.empty();
    }

//    System.out.println(quotesByPriceDtoList);

    // find the object/response containing the cheapest quote
    QuotesByPriceDto cheapestQuoteSeries = getCheapestQuoteSeries(quotesByPriceDtoList);
    log.info("============================== cheapest Quotes Series : ==============================");
    log.info(String.valueOf(cheapestQuoteSeries));

    // map results to output DTO
    CheapestQuoteDto cheapestQuoteDto = getCheapestQuoteDto(cheapestQuoteSeries);

    log.info("============================== cheapest Quote : ==============================");
    log.info(String.valueOf(cheapestQuoteDto));
//
//    // get destination in standard format using one of the RapidApi responses
    String searchDestinationId = getDestinationSearch(cheapestQuoteSeries);
//
//    // save that search
    savedDestinationSearchService.saveOrUpdateDestinationCount(searchDestinationId);

    return Mono.just(cheapestQuoteDto);
  }

  /**
   * get possible destinations from the given place
   */
  @Override
  public Mono<LocationsDto> findDestinations(String query) throws IOException, ExecutionException, InterruptedException
  {
    return webClient.get()
        .uri(BROWSE_PLACES_TEMPLATE_URL, query, API_KEY)
        .retrieve()
        .bodyToMono(LocationsDto.class)
        .log();
  }

  /**
   * Find the RapidApi response containing the cheapest quote
   *
   * @param quotesByPriceDtoList List of RapidApi responses to search in
   * @return the response containing the cheapest quote
   */
  private QuotesByPriceDto getCheapestQuoteSeries(ArrayList<QuotesByPriceDto> quotesByPriceDtoList)
  {
    QuotesByPriceDto cheapestQuoteSeries = quotesByPriceDtoList.get(0);
    for (int i = 1; i < quotesByPriceDtoList.size(); i++) {

      QuotesByPriceDto quotesObject = quotesByPriceDtoList.get(i);
      // getQuotes().get(0) - Skyscanner API puts its cheapest for the given parameters on top
      double price = quotesObject.getQuotes().get(0).getMinPrice();

      if (price < cheapestQuoteSeries.getQuotes().get(0).getMinPrice()) {
        cheapestQuoteSeries = quotesObject;
      }
    }
    return cheapestQuoteSeries;
  }

  /**
   * Get destination in standard format from a RapidApi response
   *
   * @param cheapestQuoteSeries one RapidApi response object
   * @return object with place name, RapidApi id and default search count of 1
   */
  private String getDestinationSearch(QuotesByPriceDto cheapestQuoteSeries)
  {
    QuotesByPriceDto.Quote cheapestQuote = cheapestQuoteSeries.getQuotes().get(0);

    long destinationId = cheapestQuote.getOutboundLeg().getDestinationId();

    for (int i = 0; i < cheapestQuoteSeries.getPlaces().size(); i++) {
      if (cheapestQuoteSeries.getPlaces().get(i).getPlaceId() == destinationId) {

        return cheapestQuoteSeries.getPlaces().get(i).getSkyscannerCode() + "-sky";
      }
    }

    return null;
  }

  /**
   * Map one RapidApi response to a simple quote DTO
   *
   * @param cheapestQuoteSeries the response
   * @return the quote DTO
   */
  private CheapestQuoteDto getCheapestQuoteDto(QuotesByPriceDto cheapestQuoteSeries)
  {
    CheapestQuoteDto cheapestQuoteDto = new CheapestQuoteDto();
    //   take just the top quote from the whole Series object
    QuotesByPriceDto.Quote cheapestQuote = cheapestQuoteSeries.getQuotes().get(0);

    int carriersCount = cheapestQuote.getOutboundLeg().getCarrierIds().length;
    String[] carriers = new String[carriersCount];
    for (int i = 0; i < carriersCount; i++) {
      QuotesByPriceDto.Carrier carrier = cheapestQuoteSeries.getCarriers().get(i);
      carriers[i] = carrier.getName();
    }
    cheapestQuoteDto.setCarriers(carriers);

    cheapestQuoteDto.setDate(cheapestQuote.getOutboundLeg().getDepartureDate());
    cheapestQuoteDto.setPrice(cheapestQuote.getMinPrice());
    return cheapestQuoteDto;
  }


}
