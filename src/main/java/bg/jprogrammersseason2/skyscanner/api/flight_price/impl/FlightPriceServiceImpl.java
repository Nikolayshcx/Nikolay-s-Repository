package bg.jprogrammersseason2.skyscanner.api.flight_price.impl;

import bg.jprogrammersseason2.skyscanner.api.flight_price.FlightPriceService;
import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.SkyscannerDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.SkyscannerPlacesDTO;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.api.statistics.DateStatsService;
import bg.jprogrammersseason2.skyscanner.api.statistics.MonthStatisticsService;
import bg.jprogrammersseason2.skyscanner.api.statistics.SavedDestinationSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.logging.Level;

@Service
@Transactional
public class FlightPriceServiceImpl implements FlightPriceService
{
  private final WebClient                     webClient;
  private final DateStatsService              dateStatsService;
  private final SavedDestinationSearchService savedDestinationSearchService;
  private final MonthStatisticsService        monthStatistics;
  private final String                        BROWSE_PLACES_TEMPLATE_URL;
  private final String                        BROWSE_QUOTES_TEMPLATE_URL;
  private final String                        API_KEY;

  public FlightPriceServiceImpl(WebClient webClient,
                                DateStatsService dateStatsService,
                                SavedDestinationSearchService savedDestinationSearchService, MonthStatisticsService monthStatisticsService,
                                @Value("${skyscanner.browse-places-template-url}") String browse_places_template_url,
                                @Value("${skyscanner.browse-quotes-template-url}") String browse_quotes_template_url,
                                @Value("${skyscanner.api-key}") String api_key)
  {
    this.webClient = webClient;
    this.dateStatsService = dateStatsService;
    this.savedDestinationSearchService = savedDestinationSearchService;
    this.monthStatistics = monthStatisticsService;
    BROWSE_PLACES_TEMPLATE_URL = browse_places_template_url;
    BROWSE_QUOTES_TEMPLATE_URL = browse_quotes_template_url;
    API_KEY = api_key;
  }

  @Override
  public Mono<SkyscannerDTO> getFlightPriceByDateAndLocation(PartialDate outBoundDate, PartialDate inBoundDate, String origin, String destination)
  {
    Mono.fromCallable(() -> {
            savedDestinationSearchService.saveOrUpdateDestinationCount(destination);
            dateStatsService.createOrUpdateSearchCount(inBoundDate);
            dateStatsService.createOrUpdateSearchCount(outBoundDate);
            monthStatistics.incrementMonthSearchCount(inBoundDate);
     return monthStatistics.incrementMonthSearchCount(outBoundDate);
    }).subscribeOn(Schedulers.boundedElastic()).subscribe();


    return this.webClient.get()
        .uri(BROWSE_QUOTES_TEMPLATE_URL,
            origin, destination, outBoundDate.getDate(), inBoundDate.getDate(), API_KEY)
        .retrieve()
        .bodyToMono(SkyscannerDTO.class)
        .log("reactor.netty", Level.FINE);
  }

  @Override
  public Mono<SkyscannerPlacesDTO> getSkyscannerPlaces(String place)
  {
    return this.webClient.get()
        .uri(BROWSE_PLACES_TEMPLATE_URL,
            place, API_KEY)
        .retrieve().bodyToMono(SkyscannerPlacesDTO.class)
        .log("reactor.netty", Level.FINE);
  }
}
