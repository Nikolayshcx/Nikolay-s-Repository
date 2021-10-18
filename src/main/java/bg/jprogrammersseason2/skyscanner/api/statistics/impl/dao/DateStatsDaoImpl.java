package bg.jprogrammersseason2.skyscanner.api.statistics.impl.dao;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.DateStats;
import bg.jprogrammersseason2.skyscanner.api.statistics.DateStatsDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class DateStatsDaoImpl implements DateStatsDao
{

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final JdbcTemplate               jdbcTemplate;

  public DateStatsDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate)
  {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Integer update(DateStats dateStats)
  {
    //Update is only for search count.
    return namedParameterJdbcTemplate.update(
        "UPDATE DATESEARCH " +
            " SET \"searchcount\" = :searchcount " +
            " WHERE \"date\" = :date",
        new BeanPropertySqlParameterSource(dateStats)
    );
  }

  @Override
  public Integer insert(DateStats dateStats)
  {
    //By default searchcount is 1. Only the date is needed.
    return namedParameterJdbcTemplate.update(
        "INSERT INTO DATESEARCH(\"date\") \n " +
            "VALUES (:date)",
        new BeanPropertySqlParameterSource(dateStats)
    );
  }

  @Override
  public Optional<DateStats> findByDate(LocalDate localDate)
  {
    try {
      return namedParameterJdbcTemplate.queryForObject(
          "SELECT * FROM DATESEARCH " +
              " WHERE \"date\" = :localDate",
          new MapSqlParameterSource("localDate", localDate),
          (rs, rowNum) ->
              Optional.of(new DateStats(
                  rs.getLong("id"),
                  rs.getDate("date").toLocalDate(),
                  rs.getLong("searchcount")
              ))
      );
    }
    catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Page<DateStats> findAllOrderBySearchCount(Pageable pageable)
  {
    List<DateStats> dateStats = namedParameterJdbcTemplate.query(
        "SELECT \"id\",\"date\",\"searchcount\" FROM DATESEARCH " +
            "ORDER BY \"searchcount\" DESC,\"id\"                   " +
            "OFFSET :page*:size ROWS FETCH NEXT :size ROWS ONLY     ",
        new MapSqlParameterSource("page", pageable.getPageNumber())
            .addValue("size", pageable.getPageSize()),
        (rs, rowNum) -> BeanPropertyRowMapper.newInstance(DateStats.class).mapRow(rs, rowNum)
    );

    return new PageImpl<>(dateStats, pageable, count());
  }

  @Override
  public Integer count()
  {
    return jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM DATESEARCH", Integer.class
    );
  }
}
