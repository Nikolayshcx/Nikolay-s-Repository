package bg.jprogrammersseason2.skyscanner.api.statistics.impl.dao;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.MonthStatsRow;
import bg.jprogrammersseason2.skyscanner.api.statistics.MonthStatisticsDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonthStatisticsDaoImpl implements MonthStatisticsDao
{
  private final NamedParameterJdbcTemplate template;
  private final RowMapper<MonthStatsRow> rowMapper;

  public MonthStatisticsDaoImpl(NamedParameterJdbcTemplate template, RowMapper<MonthStatsRow> rowMapper)
  {
    this.template = template;
    this.rowMapper = rowMapper;
  }

  @Override
  public Integer updateSearchCount(Integer month, Integer value)
  {
    String sql =  "UPDATE month_statistics        " +
                  "   SET search_count = :value   " +
                  "   WHERE month_number = :month ";

    SqlParameterSource parameterSource = new MapSqlParameterSource()
        .addValue("value", value)
        .addValue("month", month);

    return  template.update(sql, parameterSource);
  }

  @Override
  public Integer insert(Integer monthNumber, String monthName)
  {
    String sql =  "INSERT INTO month_statistics(month_number, month_name, search_count) " +
                  "   VALUES(:monthNumber, :monthName, :searchCount)                    ";

    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("monthNumber", monthNumber)
        .addValue("monthName", monthName)
        .addValue("searchCount", 0);

    return  template.update(sql, params);
  }

  @Override
  public MonthStatsRow getRowByMonthNumber(Integer month)
  {
    String sql =  "SELECT * FROM month_statistics " +
                  "   WHERE month_number = :month ";

    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("month", month);

    return   template.queryForObject(sql, params,rowMapper);
  }

  @Override
  public MonthStatsRow getMostSearchedMonth()
  {
    String sql =  "SELECT * FROM month_statistics " +
                  "   ORDER BY search_count DESC  " +
                  "   LIMIT 1                     ";

    return template.queryForObject(sql, new MapSqlParameterSource(),rowMapper);
  }

  @Override
  public List<MonthStatsRow> getMonthStatistics()
  {
    String sql =  "SELECT * FROM month_statistics " +
                  "   ORDER BY month_number       ";

    return template.query(sql, rowMapper);
  }
}
