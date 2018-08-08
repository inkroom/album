package cn.inkroom.web.quartz.bean;

import cn.inkroom.web.quartz.enums.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/12
 * @Time 22:04
 * @Descorption
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AjaxBean {
    private int status;
    private String message;
    private LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();

    public AjaxBean(Result result) {
        this.status = result.ordinal();
    }

    public AjaxBean(Result result, String message) {
        this.status = result.ordinal();
        this.message = message;
    }

    public LinkedHashMap<String, Object> getData() {
        return data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Result status) {
        this.status = status.ordinal();
    }

    public void put(String key, Object data) {
        this.data.put(key, data);
    }
    public void remove(String key){
        this.data.remove(key);
    }
    @Override
    @JsonIgnore
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
//        JSONObject object = new JSONObject();
//        object.put("status", status.ordinal());
//        if (message != null)
//            object.put("msg", message);
//        object.put("data", JSONObject.fromObject(this.data));
    }
}
