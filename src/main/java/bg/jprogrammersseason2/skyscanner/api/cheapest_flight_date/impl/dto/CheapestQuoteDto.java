package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto;

import lombok.Data;

@Data
public class CheapestQuoteDto
{
  private String[] carriers;
  private String date;
  private double price;

  public CheapestQuoteDto()
  {
  }
}
