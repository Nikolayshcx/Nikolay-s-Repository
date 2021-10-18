package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency
{
  private String  code;
  private String  symbol;
  private String  thousandsSeparator;
  private String  decimalSeparator;
  private Boolean symbolOnLeft;
  private Boolean spaceBetweenAmountAndSymbol;
  private Integer roundingCoefficient;
  private Integer decimalDigits;
}
