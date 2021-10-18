package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedFlightData
{
  private List<Quote> quotes;
  private List<Carrier> carriers;
  private List<Place> places;
  private List<Currency> currencies;
}
