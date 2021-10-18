package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.MonthStatsRow;

import java.util.List;

public interface MonthStatisticsDao
{

   Integer updateSearchCount(Integer month, Integer value) ;

   Integer insert(Integer monthNumber, String monthName) ;

   MonthStatsRow getRowByMonthNumber(Integer month) ;

   MonthStatsRow getMostSearchedMonth() ;

   List<MonthStatsRow> getMonthStatistics() ;
}
