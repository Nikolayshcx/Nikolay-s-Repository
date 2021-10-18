package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.MonthStatsRow;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MonthRowMapper implements RowMapper<MonthStatsRow>
{
  @Override
  public MonthStatsRow mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    return MonthStatsRow.builder()
        .monthNumber(rs.getInt("MONTH_NUMBER"))
        .monthName(rs.getString("MONTH_NAME"))
        .searchCount(rs.getInt("SEARCH_COUNT"))
        .build();
  }
}
