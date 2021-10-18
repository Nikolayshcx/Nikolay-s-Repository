package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util;

import bg.jprogrammersseason2.skyscanner.api.exception.InvalidParamsException;
import bg.jprogrammersseason2.skyscanner.validation.ValidPartialDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class PartialDateMethodArgumentResolver implements HandlerMethodArgumentResolver
{
  private final String DEFAULT_DATE = "";

  @Override
  public boolean supportsParameter(MethodParameter parameter)
  {
    return parameter.getParameterType().equals(PartialDate.class);
  }

  @Override
  public Mono<Object> resolveArgument(MethodParameter parameter,
                                      BindingContext bindingContext,
                                      ServerWebExchange exchange)
  {
    String dateParam =  exchange.getRequest().getQueryParams()
                                .getFirst(Objects.requireNonNull(parameter.getParameterName()));

    ValidPartialDate validPartialDateAnnotation = parameter.getParameterAnnotation(ValidPartialDate.class);

    if ((dateParam == null || dateParam.equals("")) &&
        validPartialDateAnnotation != null &&
        validPartialDateAnnotation.required()) {

      throw new InvalidParamsException( Collections.singletonList(  parameter.getParameterName() + " is null or empty!"),
                                        "Parameter " + parameter.getParameterName() + " is required!");
    }

    if (dateParam==null && validPartialDateAnnotation == null){
      return Mono.empty();
    }

    return Mono.just(Optional
        .ofNullable(dateParam)
        .map(PartialDate::new)
        .orElse(new PartialDate(DEFAULT_DATE)));
  }
}
