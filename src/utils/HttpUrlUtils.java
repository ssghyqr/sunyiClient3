package utils;

/**
 * 后端地址基地址
 */
public class HttpUrlUtils {
    private static final String url = "https://www.ssghdxrg.com:9090";

    public static String getUrl(String offsetUrl) {
        return url + offsetUrl;
    }
}
