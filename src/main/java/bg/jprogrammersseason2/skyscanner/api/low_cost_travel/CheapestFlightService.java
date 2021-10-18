package bg.jprogrammersseason2.skyscanner.api.low_cost_travel;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data.ReceivedFlightData;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

public interface CheapestFlightService
{
  Mono<ReceivedFlightData> getCheapestFlight(String origin, String destination, PartialDate outboundDate, PartialDate inboundDate) throws JsonProcessingException, ExecutionException, InterruptedException;
}
