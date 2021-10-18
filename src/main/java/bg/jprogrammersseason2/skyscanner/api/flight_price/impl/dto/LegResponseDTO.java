package bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LegResponseDTO
{
  private String        origin;
  private String        destination;
  private LocalDateTime departureDate;
  private List<String>  carriers;
}
