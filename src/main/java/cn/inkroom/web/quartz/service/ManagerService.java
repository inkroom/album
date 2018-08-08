package cn.inkroom.web.quartz.service;

import cn.inkroom.web.quartz.config.PathConfig;
import cn.inkroom.web.quartz.util.FileUtil;
import cn.inkroom.web.quartz.util.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private PathConfig pathConfig;

    public JSONArray init() {
        File[] files = new File(this.pathConfig.getImageBasePath()).listFiles();
        if (files != null) {
            ArrayList<Integer> counts = new ArrayList<Integer>();
            ArrayList<String> covers = new ArrayList<String>();
            ArrayList<String> names = new ArrayList<String>();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    int count = FileUtil.countImages(files[i].listFiles());
                    String cover = this.pathConfig.getImageBaseCover();
                    if (count != 0) {
                        String temp = FileUtil.getImage(0, files[i].listFiles());
                        cover = "img\\" + files[i].getName() + "\\" + temp;
                    }
                    counts.add(count);
                    covers.add(cover);
                    names.add(files[i].getName());
                }
            }
            return JsonUtil.listDirs(counts, covers, names);
        }
        return null;
    }

    public JSONArray removeDir(String name) {
        List<Map<Integer, String>> names = JsonUtil.azyNames(name);
        ArrayList<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        for (int i = 0; i < names.size(); i++) {
            Set keys = ((Map) names.get(i)).keySet();
            for (Object key : keys) {
                File file = new File(this.pathConfig.getImageBasePath() + ((Map) names.get(i)).get(key));
                boolean result = FileUtil.deleteDir(file);
                if (result) {
                    list.add(names.get(i));
                }
            }
        }
        return JsonUtil.setIsDelete(list);
    }
}
