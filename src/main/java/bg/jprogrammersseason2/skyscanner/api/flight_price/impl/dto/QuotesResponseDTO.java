package bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuotesResponseDTO
{

  private Long           quoteId;
  private String         price;
  private Boolean        direct;
  private LegResponseDTO outBoundLeg;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LegResponseDTO inBoundLeg;
  private LocalDateTime  quoteDateTime;


}
