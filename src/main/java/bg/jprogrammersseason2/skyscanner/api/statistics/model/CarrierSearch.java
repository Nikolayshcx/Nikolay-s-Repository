package bg.jprogrammersseason2.skyscanner.api.statistics.model;

import lombok.Data;

@Data
public class CarrierSearch
{
  private Long id;

  private String carrierName;

  private int carrierUseNumber;

  public CarrierSearch(String carrierName, int carrierUseNumber)
  {
    this.carrierName = carrierName;
    this.carrierUseNumber = carrierUseNumber;
  }

  public CarrierSearch(String carrierName)
  {
    this.carrierName = carrierName;
    this.carrierUseNumber = 1;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getCarrierName()
  {
    return carrierName;
  }

  public void setCarrierName(String carrierName)
  {
    this.carrierName = carrierName;
  }

  public int getCarrierUseNumber()
  {
    return carrierUseNumber;
  }

  public void setCarrierUseNumber(int carrierUseNumber)
  {
    this.carrierUseNumber = carrierUseNumber;
  }
}
