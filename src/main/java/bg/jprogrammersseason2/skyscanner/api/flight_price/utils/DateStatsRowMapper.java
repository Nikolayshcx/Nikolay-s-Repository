package bg.jprogrammersseason2.skyscanner.api.flight_price.utils;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.DateStats;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DateStatsRowMapper implements RowMapper<DateStats>
{
  @Override
  public DateStats mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    return new DateStats(
        rs.getLong("id"),
        rs.getDate("date").toLocalDate(),
        rs.getLong("searchcount"));
  }

}
