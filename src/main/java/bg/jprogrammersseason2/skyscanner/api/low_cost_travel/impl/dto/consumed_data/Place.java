package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place
{
  private String  name;
  private String  type;
  private Integer placeId;
  private String  iataCode;
  private String  skyscannerCode;
  private String  cityName;
  private String  cityId;
  private String  countryName;
}
