package cn.inkroom.web.quartz.util;

import cn.inkroom.web.quartz.entity.Size;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;

public class ImageUtil {
    public static Size getSize(File image) {
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(ImageIO.createImageInputStream(image));

            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(image);
            reader.setInput(iis, true);
            Size size = new Size();
            size.setWidth(reader.getWidth(0));
            size.setHeight(reader.getHeight(0));
            return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 截取图片
     *
     * @param org    原始图片路径
     * @param size   尺寸
     * @param target 图片输出路径
     */
    public static void converImage(String org, Size size, String target) {
        try {
            ImageIO.write(sizeImage(org, size), "jpg", new File(target));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage sizeImage(String org, Size size) {
        ImageIcon imageIcon = new ImageIcon(org);
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        double oB = (double) imageWidth / (double) imageHeight;
        double tB = (double) size.getWidth() / (double) size.getHeight();
        if (tB < oB) {//目标比例宽度更大
//            System.outJson.println(1);
            imageWidth = (int) (((double) imageHeight / (double) size.getHeight()) * size.getWidth());
        } else {//目标比例高度更大
//            System.outJson.println(2);
//            System.outJson.println((double) imageWidth / (double) width);
            imageHeight = (int) (((double) imageWidth / (double) size.getWidth()) * size.getHeight());
        }
//        System.outJson.println("ob = " + oB + "  tb = " + tB + "  height = " + imageHeight + "  width = " + imageWidth);
        g.drawImage(imageIcon.getImage(), 0, 0, size.getWidth(), size.getHeight(), 0, 0, imageWidth, imageHeight, null);
        g.dispose();
        return image;
    }

    public static BufferedImage noImage(Size size) {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        g.setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString("图片不存在", size.getWidth() / 10, size.getHeight() / 10);
        g.dispose();
        return image;
    }

    public static void main(String[] args) {
//        converImage("E:\\娱乐\\图片\\GOSICK\\GOSICK11.jpg", 400, 300, "E:\\娱乐\\图片\\cover\\1.jpg");
//        converImage("E:\\娱乐\\图片\\神的记事本\\6.jpg", 400, 300, "E:\\娱乐\\图片\\cover\\2.jpg");
    }
}
