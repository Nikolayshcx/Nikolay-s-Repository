package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto;

import lombok.Data;

@Data
public class SavedDestinationSearchDto
{
  private String placeId;
  private long   searchCount;
}
