package bg.jprogrammersseason2.skyscanner.api.common;

import bg.jprogrammersseason2.skyscanner.api.exception.InvalidParamsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

@Configuration
@Component
public class WebClientConfig
{
  private final ObjectMapper mapper;
  private final WebClient    webClient;
  private final Logger       log;
  private final String       DATE_TIME_FORMAT;

  public WebClientConfig(@Value("${skyscanner.date-time-format}") String DATE_TIME_FORMAT)
  {
    this.DATE_TIME_FORMAT = DATE_TIME_FORMAT;

    mapper = new ObjectMapper()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT))
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new JavaTimeModule());


    webClient = WebClient.create("http://partners.api.skyscanner.net/apiservices")
        .mutate().filters(exchangeFilterFunctions -> {
          exchangeFilterFunctions.add(logRequest());
          exchangeFilterFunctions.add(returnResponseBody());
        })
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(clientCodecConfigurer ->
                clientCodecConfigurer.defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(mapper)))
            .build())
        .build();

    log = LoggerFactory.getLogger(WebClientConfig.class);
  }

  @Bean(name = "webClient")
  public WebClient getWebClient()
  {
    return webClient;
  }

  @Bean(name = "objectMapper")
  @Primary
  public ObjectMapper getMapper()
  {
    return mapper;
  }

  @Bean(name = "logger")
  public Logger getLogger()
  {
    return log;
  }

  private ExchangeFilterFunction logRequest()
  {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
      clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));

      return Mono.just(clientRequest);
    });
  }

  public ExchangeFilterFunction returnResponseBody()
  {
    return ExchangeFilterFunction.ofResponseProcessor(response -> {
      if (response.statusCode().isError()) {

        return response.bodyToMono(String.class)
            .flatMap(body -> {
              try {
                return Mono.error(mapper.readValue(body, InvalidParamsException.class));
              }
              catch (JsonProcessingException e) {
                log.debug(e.getMessage());
                return Mono.just(response);
              }
            });
      }
      return Mono.just(response);
    });
  }
}
