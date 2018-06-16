package api.region.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.network.GetWebsite;
import api.region.RegionDTO;
import ui.StringUtil;

public class CityService {

	private static final Logger log = LoggerFactory.getLogger(CityService.class);

	public static final String CITIES_PREFIX_LINK = "http://placement.emploiquebec.gouv.qc.ca/mbe/ut/rechroffr/rechrcle.asp?mtcle=&pp=1&prov=http%3A%2F%2Fplacement%2Eemploiquebec%2Egouv%2Eqc%2Eca%2Fmbe%2Fut%2Frechroffr%2Ferechroffr%2Easp&date=3&creg=";
	public static final String CITIES_SUFFIX_LINK = "&CL=french";

	public static final String CITIES_ERROR = "impossible de traiter l'action";

	public static final String CITIES_RAW_START = "<tbody>";
	public static final String CITIES_RAW_END = "</tbody>";

	public static final String CITY_END = "</tr>";

	public static final String CITY_URL_BEGIN = "<td><a href=";
	public static final String CITY_NAME_BEGIN = "<td>";

	public static Map<String, List<CityDTO>> cached_cities = new HashMap<>();

	public List<CityDTO> getCities(RegionDTO r) {

		if (cached_cities.get(r.getCode()) != null) {
			return cached_cities.get(r.getCode());
		}

		ArrayList<CityDTO> cities = new ArrayList<>();

		// Convert the string to an array
		List<String> website = GetWebsite.get()
				.getWebsiteAsStringList(CITIES_PREFIX_LINK + r.getCode() + CITIES_SUFFIX_LINK);

		// Get only the cities data
		List<String> citiesRaw = StringUtil.getStringsBetween(website, CITIES_RAW_START, CITIES_RAW_END);

		CityDTO currentCity = new CityDTO();

		for (String cityLine : citiesRaw) {

			if (cityLine.contains(CITY_END)) { // Next city is coming...

				if (!(currentCity.getName() == null)) { // Because of the 'CITY_END' delimiter, there can be empty cities.
					cities.add(currentCity);
				}

				currentCity = new CityDTO();
			} else {
				if (cityLine.contains(CITY_URL_BEGIN)) {
					currentCity.setUrl(StringUtil.getStringBetween(cityLine, "<td><a href=\"", "\" >"));
					currentCity.setNumberOfOffers(StringUtil.getStringBetween(cityLine, "\" >", "</a></td>"));
				} else if (cityLine.contains(CITY_NAME_BEGIN)) {
					currentCity.setName(StringUtil.getStringBetween(cityLine, "<td>", "</td>"));
				}
			}
		}
		cached_cities.put(r.getCode(), cities);

		return cities;

	}

}
