package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.SavedDestinationSearch;

public interface SavedDestinationSearchDao
{
  // get one record with the most searches
  SavedDestinationSearch findTopByOrderBySearchCountDesc();

  SavedDestinationSearch findByPlaceId(String search);

  void save(SavedDestinationSearch search);

  void update(SavedDestinationSearch search);
}
