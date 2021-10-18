package bg.jprogrammersseason2.skyscanner.config;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDateMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Component
@RequiredArgsConstructor
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE) // checks that the app is a reactive web app
public class PartialDateArgumentResolverConfiguration implements WebFluxConfigurer
{

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer)
  {
    configurer.addCustomResolver(new PartialDateMethodArgumentResolver());
    WebFluxConfigurer.super.configureArgumentResolvers(configurer);
  }
}
