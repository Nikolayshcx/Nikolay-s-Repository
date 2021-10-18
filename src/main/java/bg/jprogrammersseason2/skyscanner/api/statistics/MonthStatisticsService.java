package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.MonthStatsRow;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;

import java.util.List;

public interface MonthStatisticsService
{
  MonthStatsRow getStatsRowByMonthNumber(Integer month);

  Integer incrementMonthSearchCount(PartialDate date);

  Integer resetMonthCounter(Integer month);

  List<MonthStatsRow> getMonthStatistics();

  MonthStatsRow getMostSearchedMonth();
}
