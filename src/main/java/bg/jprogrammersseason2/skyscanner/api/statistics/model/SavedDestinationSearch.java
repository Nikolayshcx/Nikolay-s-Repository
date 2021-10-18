package bg.jprogrammersseason2.skyscanner.api.statistics.model;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedDestinationSearch
{
  private String placeId;
  private long   searchCount;
}
