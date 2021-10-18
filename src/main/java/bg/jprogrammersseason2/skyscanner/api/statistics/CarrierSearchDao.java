package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.CarrierSearch;

import java.util.Optional;

public interface CarrierSearchDao
{
  int insert(CarrierSearch carrierSearch);
  int update(CarrierSearch carrierSearch);
  Optional<CarrierSearch> findByCarrier(String carrierName);
}
