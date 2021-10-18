package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.api.statistics.model.DateStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface DateStatsService
{
  Integer createOrUpdateSearchCount(PartialDate dateSearch);
  Page<DateStats> getAllDateSearches(Pageable pageable);
  DateStats getByDate(LocalDate localDate);
}
