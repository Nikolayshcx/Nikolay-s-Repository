package bg.jprogrammersseason2.skyscanner.api.statistics;

import bg.jprogrammersseason2.skyscanner.api.statistics.model.SavedDestinationSearch;

public interface SavedDestinationSearchService
{

  /**
   * save or update the given destination's search count
   *
   * @param placeId - the destination which will be added
   *                to DB or whose count will be increment
   *                Example: "BOJ-sky", "SOF-sky", "BEW-sky"
   */
  void saveOrUpdateDestinationCount(String placeId);

  public SavedDestinationSearch getTopDestination();
}
