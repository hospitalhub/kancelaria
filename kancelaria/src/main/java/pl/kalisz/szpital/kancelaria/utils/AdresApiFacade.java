package pl.kalisz.szpital.kancelaria.utils;

/**
 * The Class AdresApiFacade.
 */
public class AdresApiFacade {

  /** The adr2latlong. */
  public String adr2latlong =
      "http://maps.googleapis.com/maps/api/geocode/json?address=rajskowska+73,"
          + "kalisz&sensor=false&region=pl";

  /** The latlong2map. */
  public String latlong2map = "http://maps.googleapis.com/maps/api/staticmap?center=51.7643838,"
      + "18.066005&zoom=15&size=300x300&sensor=false";

  /** The latlong2pic. */
  public String latlong2pic = "http://maps.googleapis.com/maps/api/streetview?size=300x300"
      + "&location=51.7460059,18.1160926&sensor=false";

}
