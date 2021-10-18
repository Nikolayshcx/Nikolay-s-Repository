package bg.jprogrammersseason2.skyscanner.api.statistics;


import bg.jprogrammersseason2.skyscanner.api.statistics.model.DateStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface DateStatsDao
{
  Integer update(DateStats dateStats);

  Integer insert(DateStats dateStats);

  Optional<DateStats> findByDate(LocalDate localDate);

  Page<DateStats> findAllOrderBySearchCount(Pageable pageable);

  Integer count();
}
