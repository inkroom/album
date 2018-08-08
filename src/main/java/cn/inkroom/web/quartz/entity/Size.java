package cn.inkroom.web.quartz.entity;

import cn.inkroom.web.quartz.annotions.Valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Size {
    @Valid(regex = "[1-9]*[0-9]+",name = "宽度",message = "宽度")
    private int width;
    @Valid(regex = "[1-9]*[0-9]+",name = "高度",message = "高度")
    private int height;

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[1-9]*[0-9]+");
        Matcher matcher = pattern.matcher("-400");
        System.out.println(matcher.matches());
    }
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size() {
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
