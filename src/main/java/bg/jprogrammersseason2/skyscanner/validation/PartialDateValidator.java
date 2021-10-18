package bg.jprogrammersseason2.skyscanner.validation;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class PartialDateValidator implements ConstraintValidator<ValidPartialDate, PartialDate>
{
  @Override
  public boolean isValid(PartialDate date, ConstraintValidatorContext context)
  {
    String dateString = date.getDate();

    // default value is empty string, it means parameter is not present//
    if(dateString.equals("")) return true;

    boolean isAnytime = dateString.equalsIgnoreCase("anytime");

    boolean isValidYear = dateString.matches("^[\\d]{4}")
                          && Integer.parseInt(dateString) < (LocalDate.now().getYear() + 20)
                          && Integer.parseInt(dateString) >= LocalDate.now().getYear();

    boolean isValidMonth =  dateString.matches("^[\\d]{4}-[\\d]{2}")
                            && Integer.parseInt(dateString.split("-")[1]) > 0
                            && Integer.parseInt(dateString.split("-")[1]) < 13;

    boolean isValidFullDate = true;

    try {
      LocalDate localDate = LocalDate.parse(dateString);

      isValidFullDate = localDate.isAfter(LocalDate.now().minus(Period.ofDays(1)))
                        && localDate.isBefore(LocalDate.now().plus(Period.ofYears(20)));
    }
    catch (DateTimeParseException ex) {
      isValidFullDate = false;
    }

    if (!(isAnytime || isValidYear || isValidMonth || isValidFullDate)) {
      context.buildConstraintViolationWithTemplate( "Invalid date input = {" + dateString + "}, " +
                                                                  "date should not be in the past or too far ahead, " +
                                                                  "and in one of those formats: " +
                                                                  "'yyyy', 'yyyy-MM', 'yyyy-MM-dd', 'anytime' ")
          .addConstraintViolation();
      context.disableDefaultConstraintViolation();
    }

    return isAnytime || isValidYear || isValidMonth || isValidFullDate;
  }
}
