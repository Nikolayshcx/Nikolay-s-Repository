package bg.jprogrammersseason2.skyscanner.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PartialDateValidator.class)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPartialDate
{
  String message() default "Invalid date!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean required() default true;
}
