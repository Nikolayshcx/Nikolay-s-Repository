package bg.jprogrammersseason2.skyscanner.api.statistics.impl.service;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.api.statistics.model.DateStats;
import bg.jprogrammersseason2.skyscanner.api.statistics.DateStatsDao;
import bg.jprogrammersseason2.skyscanner.api.statistics.DateStatsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateStatsServiceImpl implements DateStatsService
{

  private final DateStatsDao dateStatsDao;

  public DateStatsServiceImpl(DateStatsDao dateStatsDao)
  {
    this.dateStatsDao = dateStatsDao;
  }

  @Override
  public Integer createOrUpdateSearchCount(PartialDate date)
  {
    if(date.isFullDate()) {
      DateStats foundDateStats = getByDate(date.getLocalDate());
      if (foundDateStats == null) {
        DateStats dateStatsForInsert = new DateStats(date.getLocalDate());
        return dateStatsDao.insert(dateStatsForInsert);
      }
      else {
        foundDateStats.setSearchcount(foundDateStats.getSearchcount() + 1);
        return dateStatsDao.update(foundDateStats);
      }
    }
    return -1;
  }

  @Override
  public Page<DateStats> getAllDateSearches(Pageable pageable)
  {
    return dateStatsDao.findAllOrderBySearchCount(pageable);
  }

  @Override
  public DateStats getByDate(LocalDate localDate)
  {
    return dateStatsDao.findByDate(localDate).orElse(null);
  }
}
