package bg.jprogrammersseason2.skyscanner.api.statistics.impl.service;

import bg.jprogrammersseason2.skyscanner.api.statistics.SavedDestinationSearchDao;
import bg.jprogrammersseason2.skyscanner.api.statistics.SavedDestinationSearchService;
import bg.jprogrammersseason2.skyscanner.api.statistics.model.SavedDestinationSearch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SavedDestinationSearchServiceImpl implements SavedDestinationSearchService
{
  private final SavedDestinationSearchDao savedDestinationSearchDao;
  private final Logger                    log;

  public SavedDestinationSearchServiceImpl(SavedDestinationSearchDao savedDestinationSearchDao, @Qualifier("logger") Logger log)
  {
    this.savedDestinationSearchDao = savedDestinationSearchDao;
    this.log = log;
  }

  @Override
  public void saveOrUpdateDestinationCount(String placeId)
  {
    SavedDestinationSearch destinationSearch = savedDestinationSearchDao.findByPlaceId(placeId);
    log.info("============================== destinationSearch : ==============================");
    log.info(String.valueOf(destinationSearch));
    if (null != destinationSearch) {
      long count = destinationSearch.getSearchCount();
      destinationSearch.setSearchCount(count + 1);

      savedDestinationSearchDao.update(destinationSearch);

    }
    else {
      SavedDestinationSearch newDestinationSearch = new SavedDestinationSearch();
      newDestinationSearch.setSearchCount(1);
      newDestinationSearch.setPlaceId(placeId);

      savedDestinationSearchDao.save(newDestinationSearch);
    }
  }

  public SavedDestinationSearch getTopDestination()
  {
    return savedDestinationSearchDao.findTopByOrderBySearchCountDesc();
  }
}
