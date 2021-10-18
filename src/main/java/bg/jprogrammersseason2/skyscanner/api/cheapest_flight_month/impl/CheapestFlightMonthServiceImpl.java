package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.impl;

import bg.jprogrammersseason2.skyscanner.api.statistics.CarrierSearchService;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto.*;
import bg.jprogrammersseason2.skyscanner.api.statistics.CheapestFlightMonthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CheapestFlightMonthServiceImpl implements CheapestFlightMonthService
{
  private final WebClient webClient;

  private final CarrierSearchService carrierSearchService;

  public CheapestFlightMonthServiceImpl(WebClient.Builder webClientBuilder,
                                        CarrierSearchService carrierSearchService)
  {
    this.webClient = webClientBuilder.baseUrl("https://partners.api.skyscanner.net/apiservices/").build();

    this.carrierSearchService = carrierSearchService;
  }

  @Override
  public SkyScannerDTO getCheapestFlightForTheMonth(String origin, String destination,
                                                    LocalDate month, String carrier) throws JsonProcessingException
  {

    String jsonResponse = makeRequest(origin, destination, month);

    carrierSearchService.createOrUpdateSearchCount(carrier);
    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper
        //Read case insensitive and accept LocalDate.
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .registerModule(new JavaTimeModule())

        .readValue(jsonResponse, SkyScannerDTO.class);
  }

  @Override
  public String makeRequest(String origin, String destination, LocalDate month)
  {
    YearMonth yearMonth = YearMonth.of(month.getYear(), month.getMonth());
    YearMonth yearMonthEnd = yearMonth;


    return this.webClient.get()
        //Localization BG - in the task.
        .uri("/browsequotes/v1.0/BG/bgn/bg-BG/" +
                //My parameters here.
                "{origin}/{destination}/{yearMonth}/{yearMonthEnd}" +
                //Api Key.
                "?apikey=prtl6749387986743898559646983194",

            origin, destination, yearMonth.toString(), yearMonthEnd.toString())
        .retrieve().bodyToMono(String.class).block();
  }

  public List<QuotesResponse> filterResults(List<QuotesResponse> possibleFlights, String carrier, int numberOfDays)
  {

    List<QuotesResponse> matchingFlights = new ArrayList<>();

    for (QuotesResponse flight : possibleFlights) {

      LegResponse outboundLeg = flight.getOutBoundLeg();
      LegResponse inboundLeg = flight.getInBoundLeg();

      if (outboundLeg.getCarriers().contains(carrier) &&
          inboundLeg.getCarriers().contains(carrier)) {

        long durationOfTrip = Duration.between(outboundLeg.getDepartureDate(), inboundLeg.getDepartureDate()).toMillis();
        if (TimeUnit.MILLISECONDS.toDays(durationOfTrip) <= numberOfDays &&
            durationOfTrip > 0) {

          matchingFlights.add(flight);
        }
      }
    }

    List<QuotesResponse> cheapestFlights = new ArrayList<>();

    if (matchingFlights.size() == 0) {

      return null;
    }
    else {

      int minPrice = matchingFlights.get(0).getPrice();

      for (QuotesResponse matchingFlight : matchingFlights) {

        int matchingFlightPrice = matchingFlight.getPrice();

        if (matchingFlightPrice == minPrice) {

          cheapestFlights.add(matchingFlight);
        }

        if (matchingFlightPrice < minPrice) {

          minPrice = matchingFlightPrice;
          cheapestFlights.clear();
          cheapestFlights.add(matchingFlight);
        }
      }
    }

    return cheapestFlights;
  }

  public List<QuotesResponse> convertSkyscannerDTOtoQuotesResponse(SkyScannerDTO skyscannerDTO)
  {
    //Get all quotes.
    return skyscannerDTO.getQuotes()
        .stream()
        //Map each to QuoteResponseDTO.
        .map(quote -> new QuotesResponse(

            quote.getQuoteId(),
            quote.getMinPrice(),
            quote.getDirect(),

            //QuoteResponseDTO has LegResponseDTO instead of LegDTO, another convert is needed.
            convertLegDTOtoLegResponse(quote.getOutBoundLeg(), skyscannerDTO),
            convertLegDTOtoLegResponse(quote.getInBoundLeg(), skyscannerDTO),

            quote.getQuoteDateTime()
        )).collect(Collectors.toList());

  }

  private LegResponse convertLegDTOtoLegResponse(LegDTO legDTO, SkyScannerDTO skyscannerDTO)
  {
    //This could happen when inBoundLeg is null, because it is allowed to be null.
    if (legDTO == null) {
      return null;
    }
    LegResponse legResponse = new LegResponse();
    // Origin and Destination are Ids in the response,
    //so get all places, find the wanted by id and get only the name.
    legResponse.setOrigin(
        skyscannerDTO.getPlaces().stream().filter(x -> x.getPlaceId().equals(legDTO.getOriginId())).findFirst().orElse(new PlacesDTO()).getName());

    legResponse.setDestination(
        skyscannerDTO.getPlaces().stream().filter(x -> x.getPlaceId().equals(legDTO.getDestinationId())).findFirst().orElse(new PlacesDTO()).getName());

    legResponse.setDepartureDate(legDTO.getDepartureDate());
    //Carriers are Ids in the response,
    //so get all carriers, find by ids and add them to list.
    legResponse.setCarriers(getCarrierNames(skyscannerDTO.getCarriers(), legDTO));

    return legResponse;
  }

  private List<String> getCarrierNames(List<CarriersDTO> carriersDTO, LegDTO legDTO)
  {
    List<String> carriers = new ArrayList<>();

    //Foreach carrier id find his name and add to the list.
    for (Long carrierId : legDTO.getCarrierIds()
    ) {
      carriers
          .add(carriersDTO.stream()
              .filter(x -> x.getCarrierId().equals(carrierId))
              .findFirst()
              .orElse(new CarriersDTO())
              .getName());

    }

    return carriers;
  }
}
