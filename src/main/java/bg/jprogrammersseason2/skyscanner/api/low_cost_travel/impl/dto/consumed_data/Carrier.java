package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrier
{
  private Integer carrierId;
  private String name;
}
