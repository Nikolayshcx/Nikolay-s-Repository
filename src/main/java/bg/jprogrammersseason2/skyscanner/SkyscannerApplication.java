package bg.jprogrammersseason2.skyscanner;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SkyscannerApplication
{
  public static void main(String[] args)
  {
    new SpringApplicationBuilder(SkyscannerApplication.class).web(WebApplicationType.REACTIVE).run(args);
  }
}
