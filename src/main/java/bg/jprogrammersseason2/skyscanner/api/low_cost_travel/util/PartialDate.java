package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class PartialDate
{
  private String date;

  /**
   * @return if date string is = 'anytime' return value is -1,
   * otherwise the year represented as an integer.
   */
  public Integer getYear()
  {
    return date.matches("^[\\d]{4}") ? Integer.parseInt(date.substring(0, 4)) : -1;
  }

  /**
   * @return if date string is = 'anytime' or partial date
   * representing just a year return value is -1,
   * otherwise the month part of the date.
   */
  public Integer getMonth()
  {
    return date.matches("^[\\d]{4}-[\\d]{2}") ? Integer.parseInt(date.substring(5, 7)) : -1;
  }

  /**
   * @return if date string is = 'anytime' or partial date
   * representing just a year or month return value is null,
   * otherwise the parsed string to LocalDate.
   */
  public LocalDate getLocalDate()
  {
    return isFullDate() ? LocalDate.parse(date) : null;
  }


  public boolean isFullDate(){
    return date.matches("^[\\d]{4}-[\\d]{2}-[\\d]{2}");
  }
}
