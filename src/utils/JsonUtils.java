package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * json格式变成java对象工具类
 */
public class JsonUtils {
    public static ResultInfo parseResult(String json){
        ObjectMapper om = new ObjectMapper();
        ResultInfo resultInfo = null;
        try {
            resultInfo = om.readValue(json, ResultInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultInfo;
    }

}
