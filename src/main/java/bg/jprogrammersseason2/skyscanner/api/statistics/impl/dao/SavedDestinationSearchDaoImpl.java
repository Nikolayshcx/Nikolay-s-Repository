package bg.jprogrammersseason2.skyscanner.api.statistics.impl.dao;

import bg.jprogrammersseason2.skyscanner.api.statistics.SavedDestinationSearchDao;
import bg.jprogrammersseason2.skyscanner.api.statistics.model.SavedDestinationSearch;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SavedDestinationSearchDaoImpl implements SavedDestinationSearchDao
{
  private final JdbcTemplate jdbcTemplate;

  public SavedDestinationSearchDaoImpl(JdbcTemplate jdbcTemplate)
  {
    this.jdbcTemplate = jdbcTemplate;
  }

  // mapping for a query's results
  private final RowMapper<SavedDestinationSearch> rowMapper = (resultSet, rowNum) -> {
    SavedDestinationSearch search = new SavedDestinationSearch();

    search.setPlaceId(resultSet.getString("place_id"));
    search.setSearchCount(resultSet.getLong("search_count"));

    return search;
  };

  @Override
  public void save(SavedDestinationSearch search)
  {
    String sql = "INSERT INTO saved_destination_searches (place_id, search_count) VALUES (?, ?)";

    jdbcTemplate.update(sql,
        search.getPlaceId(),
        search.getSearchCount());
  }

  @Override
  public void update(SavedDestinationSearch search)
  {
    String sql = "UPDATE saved_destination_searches SET search_count = ? WHERE place_id = ?";

    jdbcTemplate.update(sql,
        search.getSearchCount(),
        search.getPlaceId());
  }

  @Override
  public SavedDestinationSearch findByPlaceId(String placeId)
  {
    String sql = "SELECT * FROM saved_destination_searches WHERE place_id = ?";

    try {
      SavedDestinationSearch savedDestinationSearch = jdbcTemplate.queryForObject(sql, rowMapper, placeId);
      return savedDestinationSearch;
    }
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public SavedDestinationSearch findTopByOrderBySearchCountDesc()
  {
    String sql = "SELECT * " +
        "FROM saved_destination_searches " +
        "ORDER BY search_count DESC " +
        "FETCH NEXT 1 ROWS ONLY";

    return jdbcTemplate.queryForObject(sql, rowMapper);
  }

}
