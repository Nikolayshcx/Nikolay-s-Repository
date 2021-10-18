package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthStatsRow
{
  private Integer monthNumber;
  private String monthName;
  private Integer searchCount;
}
