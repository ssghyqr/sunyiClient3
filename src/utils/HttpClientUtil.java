package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: yc
 * @Description: HttpClient发送Post请求
 * @Date: 2021/07/27/18:32
 */
public class HttpClientUtil {
//    登录post请求
    public static void loginPost(String url, Map<String, String> params, SuccessListener sListener, FailListener fListener) throws IOException {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明Post请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，在Post请求中限制了浏览器后才能访问
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        // 设置token
        //httpPost.addHeader("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIDASDUzI1NiJ9.eyJleHAiOjE2Mjc0NTQzODYsInVzZXJuYW1lIjoiYWJjZCJ9.MYvNg03txeNm_KiI27fdS0KViVxWhLntDjBjiP44UYQDASCSACCSA");
        JSONObject json = new JSONObject();
        for (String key: params.keySet()) {
            json.put(key, params.get(key));
        }
//        json.put("userAccount", "11111");
//        json.put("userPassword", "11111");
        // 发送 json 类型数据，通过new StringEntity()，可将Content-Type设置为text/plain类型
        httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));

        // 设置参数（发送 普通参数 数据类型,这里发起的是需要@RequestParams得到,这是application/x-www-form-urlencoded类型
        /*
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for(String key:json.keySet()) {
            parameters.add(new BasicNameValuePair(key, json.getString(key)));
        }
        // 将Content-Type设置为application/x-www-form-urlencoded类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        httpPost.setEntity(formEntity);
		*/

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
//                成功回调函数执行
            sListener.success(EntityUtils.toString(responseEntity));
        } else {
//            失败回调函数执行
            fListener.fail();
        }

        // 关闭response、HttpClient资源
        response.close();
        httpClient.close();
    }

//    注册post请求
    public static void registerPost(String url, Map<String, String> params, SuccessListener sListener, FailListener fListener) throws IOException {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明Post请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，在Post请求中限制了浏览器后才能访问
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        // 设置token
        //httpPost.addHeader("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIDASDUzI1NiJ9.eyJleHAiOjE2Mjc0NTQzODYsInVzZXJuYW1lIjoiYWJjZCJ9.MYvNg03txeNm_KiI27fdS0KViVxWhLntDjBjiP44UYQDASCSACCSA");
        JSONObject json = new JSONObject();
        for (String key: params.keySet()) {
            json.put(key, params.get(key));
        }
        // 发送 json 类型数据，通过new StringEntity()，可将Content-Type设置为text/plain类型
        httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));

        // 设置参数（发送 普通参数 数据类型,这里发起的是需要@RequestParams得到,这是application/x-www-form-urlencoded类型
        /*
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for(String key:json.keySet()) {
            parameters.add(new BasicNameValuePair(key, json.getString(key)));
        }
        // 将Content-Type设置为application/x-www-form-urlencoded类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        httpPost.setEntity(formEntity);
		*/

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
//                成功回调函数执行
            sListener.success(EntityUtils.toString(responseEntity));
        } else {
//            失败回调函数执行
            fListener.fail();
        }

        // 关闭response、HttpClient资源
        response.close();
        httpClient.close();
    }

    //    查询是否为好友
    public static void contactCheckPost(String url, Map<String, Object> params, SuccessListener sListener, FailListener fListener) throws IOException {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明Post请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，在Post请求中限制了浏览器后才能访问
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        // 设置token
        //httpPost.addHeader("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIDASDUzI1NiJ9.eyJleHAiOjE2Mjc0NTQzODYsInVzZXJuYW1lIjoiYWJjZCJ9.MYvNg03txeNm_KiI27fdS0KViVxWhLntDjBjiP44UYQDASCSACCSA");
        JSONObject json = new JSONObject();
        for (String key: params.keySet()) {
            json.put(key, params.get(key));
        }
        // 发送 json 类型数据，通过new StringEntity()，可将Content-Type设置为text/plain类型
        httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));

        // 设置参数（发送 普通参数 数据类型,这里发起的是需要@RequestParams得到,这是application/x-www-form-urlencoded类型
        /*
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for(String key:json.keySet()) {
            parameters.add(new BasicNameValuePair(key, json.getString(key)));
        }
        // 将Content-Type设置为application/x-www-form-urlencoded类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        httpPost.setEntity(formEntity);
		*/

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
//                成功回调函数执行
            sListener.success(EntityUtils.toString(responseEntity));
        } else {
//            失败回调函数执行
            fListener.fail();
        }

        // 关闭response、HttpClient资源
        response.close();
        httpClient.close();
    }

    //    添加为好友
    public static void contactAddPost(String url, Map<String, Object> params, SuccessListener sListener, FailListener fListener) throws IOException {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明Post请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，在Post请求中限制了浏览器后才能访问
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        // 设置token
        //httpPost.addHeader("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIDASDUzI1NiJ9.eyJleHAiOjE2Mjc0NTQzODYsInVzZXJuYW1lIjoiYWJjZCJ9.MYvNg03txeNm_KiI27fdS0KViVxWhLntDjBjiP44UYQDASCSACCSA");
        JSONObject json = new JSONObject();
        for (String key: params.keySet()) {
            json.put(key, params.get(key));
        }
        // 发送 json 类型数据，通过new StringEntity()，可将Content-Type设置为text/plain类型
        httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));

        // 设置参数（发送 普通参数 数据类型,这里发起的是需要@RequestParams得到,这是application/x-www-form-urlencoded类型
        /*
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for(String key:json.keySet()) {
            parameters.add(new BasicNameValuePair(key, json.getString(key)));
        }
        // 将Content-Type设置为application/x-www-form-urlencoded类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        httpPost.setEntity(formEntity);
		*/

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
//                成功回调函数执行
            sListener.success(EntityUtils.toString(responseEntity));
        } else {
//            失败回调函数执行
            fListener.fail();
        }

        // 关闭response、HttpClient资源
        response.close();
        httpClient.close();
    }

    //    用户加入群聊
    public static void addGroupMemberPost(String url, Map<String, Object> params, SuccessListener sListener, FailListener fListener) throws IOException {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明Post请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，在Post请求中限制了浏览器后才能访问
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        // 设置token
        //httpPost.addHeader("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIDASDUzI1NiJ9.eyJleHAiOjE2Mjc0NTQzODYsInVzZXJuYW1lIjoiYWJjZCJ9.MYvNg03txeNm_KiI27fdS0KViVxWhLntDjBjiP44UYQDASCSACCSA");
        JSONObject json = new JSONObject();
        for (String key: params.keySet()) {
            json.put(key, params.get(key));
        }
        // 发送 json 类型数据，通过new StringEntity()，可将Content-Type设置为text/plain类型
        httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));

        // 设置参数（发送 普通参数 数据类型,这里发起的是需要@RequestParams得到,这是application/x-www-form-urlencoded类型
        /*
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for(String key:json.keySet()) {
            parameters.add(new BasicNameValuePair(key, json.getString(key)));
        }
        // 将Content-Type设置为application/x-www-form-urlencoded类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        httpPost.setEntity(formEntity);
		*/

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
//                成功回调函数执行
            sListener.success(EntityUtils.toString(responseEntity));
        } else {
//            失败回调函数执行
            fListener.fail();
        }

        // 关闭response、HttpClient资源
        response.close();
        httpClient.close();
    }
}
