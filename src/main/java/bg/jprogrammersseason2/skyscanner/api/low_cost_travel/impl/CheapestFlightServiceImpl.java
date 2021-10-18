package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.CheapestFlightService;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data.ReceivedFlightData;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.api.statistics.DateStatsService;
import bg.jprogrammersseason2.skyscanner.api.statistics.MonthStatisticsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Service
public class CheapestFlightServiceImpl implements CheapestFlightService
{
  private final WebClient              webClient;
  private final MonthStatisticsService monthStatistics;
  private final DateStatsService       dateStatsService;
  private final String                 API_KEY;
  private final String                 BROWSE_QUOTES_URL_TEMPLATE;

  public CheapestFlightServiceImpl(@Qualifier("webClient") WebClient webClient,
                                   MonthStatisticsService monthStatistics,
                                   DateStatsService dateStatsService,
                                   @Value("${skyscanner.api-key}") String api_key,
                                   @Value("${skyscanner.browse-quotes-template-url}") String browse_quotes_url_template)
  {
    this.webClient = webClient;
    this.monthStatistics = monthStatistics;
    this.dateStatsService = dateStatsService;
    API_KEY = api_key;
    BROWSE_QUOTES_URL_TEMPLATE = browse_quotes_url_template;
  }

  @Override
  public Mono<ReceivedFlightData> getCheapestFlight(String origin, String destination, PartialDate outboundDate, PartialDate inboundDate) throws JsonProcessingException, ExecutionException, InterruptedException
  {

    //############- Adding statistics data for searched month -##################/
    Mono.fromCallable(() -> {
        dateStatsService.createOrUpdateSearchCount(inboundDate);
        dateStatsService.createOrUpdateSearchCount(outboundDate);
        monthStatistics.incrementMonthSearchCount(inboundDate);
        monthStatistics.incrementMonthSearchCount(outboundDate);
      return 1;
    }).publishOn(Schedulers.boundedElastic()).subscribe();
    //###########################################################################/

    return webClient.get()
                    .uri(BROWSE_QUOTES_URL_TEMPLATE,
                        origin, destination,
                        outboundDate.getDate(), inboundDate.getDate(),
                        API_KEY)
                    .retrieve()
                    .bodyToMono(ReceivedFlightData.class)
                    .log("reactor.netty", Level.FINE);
  }
}
