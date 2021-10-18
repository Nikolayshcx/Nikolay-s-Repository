package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponse
{
  private String name;
  private String city;
  private String country;
}
