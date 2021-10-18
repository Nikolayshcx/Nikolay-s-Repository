package bg.jprogrammersseason2.skyscanner.api.statistics.impl.service;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.MonthStatsRow;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.api.statistics.MonthStatisticsDao;
import bg.jprogrammersseason2.skyscanner.api.statistics.MonthStatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthStatisticsServiceImpl implements MonthStatisticsService
{
  MonthStatisticsDao monthStatisticsDao;

  public MonthStatisticsServiceImpl(MonthStatisticsDao monthStatisticsDao)
  {
    this.monthStatisticsDao = monthStatisticsDao;
  }

  @Override
  public MonthStatsRow getStatsRowByMonthNumber(Integer month)
  {
    return monthStatisticsDao.getRowByMonthNumber(month);
  }

  @Override
  public Integer incrementMonthSearchCount(PartialDate date)
  {
    if(date.getMonth()!=-1) {
      Integer searchCount = monthStatisticsDao.getRowByMonthNumber(date.getMonth())
                                              .getSearchCount();

      return  monthStatisticsDao.updateSearchCount(date.getMonth(), ++searchCount);
    }
    return -1;
  }

  @Override
  public Integer resetMonthCounter(Integer month)
  {
    return monthStatisticsDao.updateSearchCount(month,0);
  }

  @Override
  public List<MonthStatsRow> getMonthStatistics(){
    return monthStatisticsDao.getMonthStatistics();
  }

  @Override
  public MonthStatsRow getMostSearchedMonth(){
    return monthStatisticsDao.getMostSearchedMonth();
  }
}
