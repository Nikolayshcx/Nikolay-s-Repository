package bg.jprogrammersseason2.skyscanner.api.statistics.impl.dao;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.CarrierSearch;
import bg.jprogrammersseason2.skyscanner.api.statistics.CarrierSearchDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CarrierSearchDaoImpl implements CarrierSearchDao
{
  private final NamedParameterJdbcTemplate parameterJdbcTemplate;

  public CarrierSearchDaoImpl(NamedParameterJdbcTemplate parameterJdbcTemplate)
  {
    this.parameterJdbcTemplate = parameterJdbcTemplate;
  }

  @Override
  public int update(CarrierSearch carrierSearch)
  {
    return parameterJdbcTemplate.update(
        "UPDATE carrier_searches " +
            " SET \"CARRIER_USE_NUMBER\" = :carrierUseNumber " +
            " WHERE \"CARRIER_NAME\" = :carrierName",
        new BeanPropertySqlParameterSource(carrierSearch)
    );
  }

  @Override
  public int insert(CarrierSearch carrierSearch)
  {
    return parameterJdbcTemplate.update(
        "INSERT INTO carrier_searches(\"CARRIER_NAME\") " +
            "VALUES (:carrierName)",
        new BeanPropertySqlParameterSource(carrierSearch)
    );
  }

  @Override
  public Optional<CarrierSearch> findByCarrier(String carrierName)
  {

    try {
      return parameterJdbcTemplate.queryForObject(
          "SELECT * FROM carrier_searches " +
              " WHERE \"CARRIER_NAME\" = :carrierName",
          new MapSqlParameterSource("carrierName", carrierName),
          (resultSet, rowNum) ->
              Optional.of(new CarrierSearch(
                  resultSet.getString("CARRIER_NAME"),
                  resultSet.getInt("CARRIER_USE_NUMBER")
              ))
      );
    }
    catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
