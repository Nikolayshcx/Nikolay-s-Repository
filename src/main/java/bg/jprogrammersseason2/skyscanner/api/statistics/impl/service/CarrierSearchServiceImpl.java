package bg.jprogrammersseason2.skyscanner.api.statistics.impl.service;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.CarrierSearch;
import bg.jprogrammersseason2.skyscanner.api.statistics.CarrierSearchDao;
import bg.jprogrammersseason2.skyscanner.api.statistics.CarrierSearchService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrierSearchServiceImpl implements CarrierSearchService
{
  private final CarrierSearchDao carrierSearchDao;

  public CarrierSearchServiceImpl(CarrierSearchDao carrierSearchDao)
  {
    this.carrierSearchDao = carrierSearchDao;
  }

  @Override
  public int createOrUpdateSearchCount(String carrierName)
  {

    Optional<CarrierSearch> carrierToFind =
        carrierSearchDao.findByCarrier(carrierName);

    if (carrierToFind.isPresent()) {

      CarrierSearch foundCarrier = carrierToFind.get();
      foundCarrier.setCarrierUseNumber(foundCarrier.getCarrierUseNumber() + 1);
      return carrierSearchDao.update(foundCarrier);
    }
    else {

      return carrierSearchDao.insert(new CarrierSearch(carrierName));
    }
  }
}
