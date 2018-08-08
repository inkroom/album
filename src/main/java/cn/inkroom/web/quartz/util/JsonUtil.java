package cn.inkroom.web.quartz.util;

import cn.inkroom.web.quartz.entity.Size;
import cn.inkroom.web.quartz.enums.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
    public static JSONArray listFiles(File[] files) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                JSONObject object = new JSONObject();
                Size size = ImageUtil.getSize(files[i]);
                object.put("width", size.getWidth());
                object.put("height", size.getHeight());
                object.put("name", files[i].getName());

                array.add(object);
            }
        }
        if (array.size() == 0) {
            return null;
        }
        return array;
    }

    private static JSONObject setStatus(int status) {
        JSONObject object = new JSONObject();
        object.put("status", status);
        return object;
    }

    public static JSONObject setStatus(Result status) {
        return setStatus(status.ordinal());
    }

    public static JSONArray listDirs(List<Integer> counts, List<String> covers, List<String> names) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < counts.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("count", counts.get(i));
            object.put("name", names.get(i));
            object.put("cover", covers.get(i));

            array.add(object);
        }
        return array;
    }

    public static ArrayList<Map<Integer, String>> azyNames(String name) {
        JSONArray array = JSONArray.fromObject(name);
        ArrayList<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        for (int i = 0; i < array.size(); i++) {
            Map<Integer, String> map = new HashMap<Integer, String>();
            map.put((array.getJSONObject(i).getInt("order")), array.getJSONObject(i).getString("name"));

            list.add(map);
        }
        return list;
    }

    public static JSONArray setIsDelete(List<Map<Integer, String>> list) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            Set<Integer> keys = ((Map) list.get(i)).keySet();
            for (Integer kes : keys) {
                JSONObject object = new JSONObject();
                object.put("order", kes);
                object.put("name", ((Map) list.get(i)).get(kes));

                array.add(object);
            }
        }
        return array;
    }

    public static boolean checkJson(String json) {
        try {
            JSONObject.fromObject(json);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
