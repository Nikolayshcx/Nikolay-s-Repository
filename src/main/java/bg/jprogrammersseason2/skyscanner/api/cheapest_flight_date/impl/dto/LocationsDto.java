package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class LocationsDto
{
  @JsonProperty("Places") private ArrayList<Place> places;
  //@JsonProperty("Message") private String message;
  public LocationsDto()
  {
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown=true)
  public static class Place
  {
    @JsonProperty("PlaceId") private String placeId;

    public Place()
    {
    }
  }
}
