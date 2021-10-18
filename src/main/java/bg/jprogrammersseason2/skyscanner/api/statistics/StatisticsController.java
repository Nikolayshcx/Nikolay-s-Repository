package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.SavedDestinationSearchDto;
import bg.jprogrammersseason2.skyscanner.api.statistics.model.DateStats;
import bg.jprogrammersseason2.skyscanner.api.statistics.model.SavedDestinationSearch;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Validated
public class StatisticsController
{
  private final DateStatsService       dateStatsService;
  private final MonthStatisticsService monthStatisticsService;
  private final SavedDestinationSearchService savedDestinationSearchService;

  public StatisticsController(	DateStatsService dateStatsService,
  				MonthStatisticsService monthStatisticsService,
                              	SavedDestinationSearchService savedDestinationSearchService)
  {
    this.dateStatsService = dateStatsService;
    this.monthStatisticsService = monthStatisticsService;
    this.savedDestinationSearchService = savedDestinationSearchService;
  }

  @GetMapping("date-searches")
  public ResponseEntity<Object> getDateStatisticsOrderByMostSearched(Pageable pageable)
  {
    List<DateStats> dateStatsList = dateStatsService.getAllDateSearches(pageable).toList();

    return ResponseEntity.ok(dateStatsList);
  }

  @GetMapping("/month-statistics")
  @ResponseStatus(HttpStatus.OK)
  public Mono<?> getMonthStatisticsService(){
    return Mono.just(monthStatisticsService.getMonthStatistics());
  }

  @GetMapping("/most-searched-month")
  @ResponseStatus(HttpStatus.OK)
  public Mono<?> getMostSearchedMonth(){
    return  Mono.just(monthStatisticsService.getMostSearchedMonth());
  }
  @GetMapping("/topDestination")
  public ResponseEntity<SavedDestinationSearchDto> getTopDestination()
  {
    SavedDestinationSearch savedDestinationSearch = savedDestinationSearchService.getTopDestination();

    ModelMapper modelMapper = new ModelMapper();
    SavedDestinationSearchDto savedDestinationSearchDto = modelMapper.map(savedDestinationSearch, SavedDestinationSearchDto.class);

    return ResponseEntity.ok(savedDestinationSearchDto);
    }
}
