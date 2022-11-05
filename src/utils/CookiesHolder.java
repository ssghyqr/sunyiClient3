package utils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
/**
 * 设置访问请求Cookie包
 */
public class CookiesHolder {

    private static CookieStore cookieStore = new BasicCookieStore();

    public static synchronized CookieStore getCookieStore() {
        return cookieStore;
    }

}

