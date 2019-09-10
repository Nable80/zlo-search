package info.xonix.zlo.search.utils;

public class URLUtil {
	public static String getURLWithProto(String siteURL) {
		if (siteURL == null)
			return null;
		if (!siteURL.startsWith("http")) {// either http:// or https://
			siteURL = "http://" + siteURL;
		}
		return siteURL;
	}

}
