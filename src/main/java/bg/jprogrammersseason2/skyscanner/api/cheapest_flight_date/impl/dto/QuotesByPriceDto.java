package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotesByPriceDto
{
  private ArrayList<Quote> quotes;
  private ArrayList<Carrier> carriers;
  private ArrayList<Place> places;

  @Builder
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Quote
  {
    private double minPrice;
    private OutboundLeg outboundLeg;
  }

  @Builder
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OutboundLeg
  {
    private long[] carrierIds;
    private long destinationId;
    private String departureDate;
  }

  @Builder
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Carrier
  {
    private long carrierId;
    private String name;
  }

  @Builder
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Place
  {
    private String name;
    private long placeId;
    private String skyscannerCode;
  }



}
