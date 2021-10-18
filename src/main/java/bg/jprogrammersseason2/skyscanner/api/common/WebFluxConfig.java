package bg.jprogrammersseason2.skyscanner.api.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;


@Configuration
public class WebFluxConfig implements WebFluxConfigurer
{
  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer)
  {
    ReactivePageableHandlerMethodArgumentResolver resolver = new ReactivePageableHandlerMethodArgumentResolver();
    resolver.setPageParameterName("page");
    resolver.setSizeParameterName("size");
    resolver.setFallbackPageable(PageRequest.of(0, 10));
    configurer.addCustomResolver(resolver);
    WebFluxConfigurer.super.configureArgumentResolvers(configurer);
  }
}
