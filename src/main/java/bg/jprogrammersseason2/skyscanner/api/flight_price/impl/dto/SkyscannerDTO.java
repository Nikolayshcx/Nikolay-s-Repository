package bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkyscannerDTO
{
  private List<QuotesDTO>      quotes;
  private List<DestinationDTO> places;
  private List<CarriersDTO>    carriers;
  private List<CurrencyDTO>    currencies;
}
