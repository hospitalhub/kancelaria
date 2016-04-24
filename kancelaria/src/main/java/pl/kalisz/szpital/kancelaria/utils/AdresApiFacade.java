package pl.kalisz.szpital.kancelaria.utils;

import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.javadocmd.simplelatlng.LatLng;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Image;

/**
 * The Class AdresApiFacade.
 */
public class AdresApiFacade {

	/** The adr2latlong. */
	private final static String adr2latlong = "http://maps.googleapis.com/maps/api/geocode/json?address=XXX&sensor=false&region=pl";

	/** The latlong2map. */
	private final static String latlong2map = "http://maps.googleapis.com/maps/api/staticmap?center=XXXLAT,"
			+ "XXXLONG&zoom=15&size=300x300&sensor=false";

	/** The latlong2pic. */
	private final static String latlong2pic = "http://maps.googleapis.com/maps/api/streetview?size=300x300"
			+ "&location=XXXLAT,XXXLONG&sensor=false";

	private static JSONObject findAdres(String adres) throws JSONException,
			IOException {
		URL uri = new URL(adr2latlong.replace("XXX", adres));
		JSONTokener tokener = new JSONTokener(uri.openStream());
		JSONObject root = new JSONObject(tokener);
		return root;
	}

	private static LatLng getLatLng(JSONObject root) throws JSONException,
			IOException {
		JSONObject position = root.getJSONArray("results").getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location");
		return new LatLng(position.getDouble("lat"), position.getDouble("lng"));
	}

	private static String getFoundAddress(JSONObject root)
			throws JSONException, IOException {
		return root.getJSONArray("results").getJSONObject(0)
				.get("formatted_address").toString();
	}

	private static Image res2img(ExternalResource externalResource, String caption) {
		externalResource.setMIMEType("image/png");
		Image image = new Image(caption, externalResource);
		return image;
	}

	private static Image getMap(LatLng ll, String caption) throws JSONException, IOException {
		String url = latlong2map.replace("XXXLAT",
				new Double(ll.getLatitude()).toString()).replace("XXXLONG",
				new Double(ll.getLongitude()).toString());
		return res2img(new ExternalResource(url), caption);
	}

	private static Image getPic(LatLng ll, String caption) throws JSONException, IOException {
		String url = latlong2pic.replace("XXXLAT",
				new Double(ll.getLatitude()).toString()).replace("XXXLONG",
				new Double(ll.getLongitude()).toString());
		return res2img(new ExternalResource(url), caption);
	}

	public static Image[] getGeoPics(String adr) {
		String str = "?";
		try {
			System.out.println("google geo loc");
			JSONObject root = AdresApiFacade.findAdres(adr);
			LatLng ll = AdresApiFacade.getLatLng(root);
			str = AdresApiFacade.getFoundAddress(root).replace("Poland", "Polska");
			Image image = AdresApiFacade.getMap(ll, str);
			Image image2 = AdresApiFacade.getPic(ll, str);
			return new Image[] {image, image2};
		} catch (Exception e) {
			System.err.println("Err getting geo-data from google: " + str);
		}
		return null;
	}

}
