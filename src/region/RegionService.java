package region;

import java.util.ArrayList;
import java.util.List;

import alde.commons.network.WebsiteReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ui.StringUtil;

public class RegionService {

	private static final Logger log = LoggerFactory.getLogger(RegionService.class);

	WebsiteReader websiteReader;

	public RegionService(WebsiteReader websiteReader) {
		this.websiteReader = websiteReader;
	}

	private static final String REGIONS_LINK = "http://placement.emploiquebec.gouv.qc.ca/mbe/ut/rechroffr/erechroffr.asp?CL=french";
	public static final String REGIONS_ERROR = "The page cannot be found";

	public static final String CITIES_RAW_START = "<ul class=\"regnlist\">";
	public static final String CITIES_RAW_END = "<li><input type=\"radio\" name=\"MB_ST_MOBL\"";

	public static final String REGION_START = "<li><input type=\"checkbox\" name=\"MB_CT_REGN_ADMN\"";

	public List<Region> getRegions() {
		ArrayList<Region> regions = new ArrayList<>();

		List<String> website = websiteReader.getWebsiteAsStringList(REGIONS_LINK);

		List<String> regionsRaw = StringUtil.getStringsBetween(website, CITIES_RAW_START, CITIES_RAW_END);

		for (String regionLine : regionsRaw) {

			if (regionLine.contains("<li><input type=\"checkbox\" name=\"MB_CT_REGN_ADMN\"")) { // Next city is coming...
				Region region = new Region();

				region.setCode(StringUtil.getStringBetween(regionLine, "MB_CT_REGN_ADMNMB_CT_REGN_ADMN_", "\">"));
				region.setName(StringUtil
						.html2text(StringUtil.getStringBetween(regionLine, region.getCode() + "\">", "</label>")));

				regions.add(region);
			}
		}

		return regions;
	}

}
