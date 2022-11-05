package utils;
/**
 * 返回的结果集
 */
public class ResultInfo {

    private String code;
    private String msg;
    private Object data;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + msg + '\'' +
                '}';
    }
}
