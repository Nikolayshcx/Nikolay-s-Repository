package bg.jprogrammersseason2.skyscanner.api.statistics.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateStats
{
  private Long      id;
  private LocalDate date;
  private Long      searchcount;

  public DateStats(LocalDate date)
  {
    this.date = date;
  }
}
