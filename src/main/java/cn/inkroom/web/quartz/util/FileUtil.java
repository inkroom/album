package cn.inkroom.web.quartz.util;

import java.io.File;

public class FileUtil {
    public static int countFiles(File[] files) {
        int count = 0;
        for (File file : files) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    public static int countImages(File[] files) {
        int count = 0;
        if (files == null) {
            return count;
        }
        for (File file : files) {
            String name = file.getName();
            if (isImage(name)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取文件数组中的指定图片
     *
     * @param index 第几张图片
     * @param files 文件集合
     * @return 没有返回null
     */
    public static String getImage(int index, File[] files) {
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            if (!isImage(name)) {//跳过不是图片的文件
                index++;
                continue;
            }
            if ((isImage(name)) && (i == index)) {
                return name;
            }
        }
        return null;
    }

    public static boolean isImage(String name) {
        return (name.endsWith(".jpg")) || (name.endsWith(".png")) || (name.endsWith(".gif")) || (name.endsWith(".jpeg"));
    }

    public static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] child = file.list();
            for (int i = 0; i < child.length; i++) {
                boolean result = deleteDir(new File(file, child[i]));
                if (!result) {
                    return false;
                }
            }
            return file.delete();
        }
        return file.delete();
    }
}
