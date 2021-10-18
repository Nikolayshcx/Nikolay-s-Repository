package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.FlightResponse;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.PlaceResponse;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.QuoteResponse;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data.*;

import java.util.List;

public class FlightDataMapper
{
  public static QuoteResponse mapFlightDataToResponse(ReceivedFlightData flightData)
  {

    if (flightData.getQuotes().size() == 0) {
      return new QuoteResponse();
    }

    Quote quote = flightData.getQuotes().get(0);
    List<Place> places = flightData.getPlaces();
    List<Carrier> carriers = flightData.getCarriers();

    return QuoteResponse.builder()
        .price(quote.getMinPrice().toString() + flightData.getCurrencies().get(0).getSymbol())
        .direct(quote.getDirect())
        .outboundLeg(createFlightResponseFromFlightData(carriers, quote.getOutboundLeg(), places))
        .inboundLeg(createFlightResponseFromFlightData(carriers, quote.getInboundLeg(), places))
        .build();
  }

  private static FlightResponse createFlightResponseFromFlightData(List<Carrier> carriers, FlightLeg flightLeg, List<Place> places)
  {
    return flightLeg == null ? null :
        FlightResponse.builder()
            .carrier(getCarrierNameFromFlightData(flightLeg, carriers))
            .origin(getPlaceInfoFromId(flightLeg.getOriginId(), places))
            .destination(getPlaceInfoFromId(flightLeg.getDestinationId(), places))
            .departureDate(flightLeg.getDepartureDate()).build();
  }

  public static PlaceResponse mapPlaceToResponseDto(Place place)
  {
    if (place == null) {
      throw new IllegalArgumentException("Cannot map null Place object!");
    }
    return PlaceResponse.builder()
        .name(place.getName())
        .city(place.getCityName())
        .country(place.getCountryName())
        .build();
  }

  public static String getCarrierNameFromFlightData(FlightLeg flightLeg, List<Carrier> carriers)
  {
    if (flightLeg == null) {
      return "";
    }

    Integer flightLegCarrierId = flightLeg.getCarrierIds().get(0);
    Carrier flightLegCarrier = carriers.stream()
        .filter(c -> c.getCarrierId().equals(flightLegCarrierId)).findFirst().orElse(null);

    return flightLegCarrier != null ? flightLegCarrier.getName() : "";
  }

  public static PlaceResponse getPlaceInfoFromId(Integer placeID, List<Place> places)
  {

    Place outboundLegOrigin = places.stream()
        .filter(p -> p.getPlaceId().equals(placeID)).findFirst().orElse(null);

    return mapPlaceToResponseDto(outboundLegOrigin);
  }

}
