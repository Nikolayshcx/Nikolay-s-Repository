package bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesDTO
{
  private Long          quoteId;
  private Integer       minPrice;
  private Boolean       direct;
  private LegDTO        outBoundLeg;
  private LegDTO        inBoundLeg;
  private LocalDateTime quoteDateTime;
}
