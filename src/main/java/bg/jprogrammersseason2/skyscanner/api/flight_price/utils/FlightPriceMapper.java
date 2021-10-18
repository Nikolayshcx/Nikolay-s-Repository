package bg.jprogrammersseason2.skyscanner.api.flight_price.utils;

import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightPriceMapper
{
  public List<QuotesResponseDTO> convertSkyscannerDTOtoQuotesResponseDTO(SkyscannerDTO skyscannerDTO)
  {
    //Get all quotes.
    return skyscannerDTO.getQuotes()
        .stream()
        //Map each to QuoteResponseDTO.
        .map(quote -> new QuotesResponseDTO(

            quote.getQuoteId(),
            quote.getMinPrice() + " " + skyscannerDTO.getCurrencies().get(0).getSymbol(),
            quote.getDirect(),

            //QuoteResponseDTO has LegResponseDTO instead of LegDTO, another convert is needed.
            convertLegDTOtoLegResponseDTO(quote.getOutBoundLeg(), skyscannerDTO),
            convertLegDTOtoLegResponseDTO(quote.getInBoundLeg(), skyscannerDTO),

            quote.getQuoteDateTime()
        )).collect(Collectors.toList());

  }

  private LegResponseDTO convertLegDTOtoLegResponseDTO(LegDTO legDTO, SkyscannerDTO skyscannerDTO)
  {
    //This could happen when inBoundLeg is null, because it is allowed to be null.
    if (legDTO == null) {
      return null;
    }
    LegResponseDTO legResponseDTO = new LegResponseDTO();
    // Origin and Destination are Ids in the response,
    //so get all places, find the wanted by id and get only the name.
    legResponseDTO.setOrigin(
        skyscannerDTO.getPlaces().stream().filter(x -> x.getPlaceId().equals(legDTO.getOriginId())).findFirst().orElse(new DestinationDTO()).getName());

    legResponseDTO.setDestination(
        skyscannerDTO.getPlaces().stream().filter(x -> x.getPlaceId().equals(legDTO.getDestinationId())).findFirst().orElse(new DestinationDTO()).getName());

    legResponseDTO.setDepartureDate(legDTO.getDepartureDate());
    //Carriers are Ids in the response,
    //so get all carriers, find by ids and add them to list.
    legResponseDTO.setCarriers(getCarrierNames(skyscannerDTO.getCarriers(), legDTO));

    return legResponseDTO;
  }

  private List<String> getCarrierNames(List<CarriersDTO> carriersDTO, LegDTO legDTO)
  {
    List<String> carriers = new ArrayList<>();

    //Foreach carrier id find his name and add to the list.
    for (Long carrierId : legDTO.getCarrierIds()
    ) {
      carriers
          .add(carriersDTO.stream()
              .filter(x -> x.getCarrierId().equals(carrierId))
              .findFirst()
              .orElse(new CarriersDTO())
              .getName());

    }

    return carriers;
  }
}
