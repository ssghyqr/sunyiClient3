package utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * java发起一次post请求
 */
public class PostUtils {
    public static void post(String url, SuccessListener successListener, FailListener failListener) {
        postWithParams(url, new HashMap<>(), successListener, failListener);
    }

    public static void postWithParams(String url, Map<String, String> params,SuccessListener sListener,FailListener fListener) {
//        将参数变成封装成数据,NameValuePair是自带的类型
        List<NameValuePair> pairs = generatePairs(params);
//        创建一次cookie请求访问
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(CookiesHolder.getCookieStore()).build();
        CloseableHttpResponse response = null;
        try {
//            httppost请求设置
            HttpPost httpPost = new HttpPost(url);
//            HttpPut httpPost = new HttpPut(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(3000) //服务器响应超时时间
                    .setConnectTimeout(3000) //连接服务器超时时间
                    .build();
            httpPost.setConfig(requestConfig);

//            设置编码格式
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            httpPost.setEntity(entity);
//            设置响应头
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            // 由客户端执行(发送)请求
            response = httpClient.execute(httpPost);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
//                成功回调函数执行
                sListener.success(EntityUtils.toString(responseEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
//            失败回调函数执行
            fListener.fail();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * map集合转化为键值
     *
     * @param params
     * @return
     */
    private static List<NameValuePair> generatePairs(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return Collections.emptyList();
        }
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null) {
                continue;
            }
            pairs.add(new BasicNameValuePair(key, value));
        }
        return pairs;
    }
}
