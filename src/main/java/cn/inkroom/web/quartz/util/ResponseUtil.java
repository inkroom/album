package cn.inkroom.web.quartz.util;


import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.entity.Size;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/12
 * @Time 22:33
 * @Descorption
 */
public class ResponseUtil {
    public static void outJson(HttpServletResponse response, String value) throws Exception {
        if (!response.isCommitted()) {
            response.setContentType(Constants.RESPONSE_CONTENT_TYPE_JSON);
            PrintWriter out = response.getWriter();
            out.write(value);
            out.flush();
            out.close();
        }
    }

    public static void out404(HttpServletRequest request, HttpServletResponse response, String path) throws Exception {
        if (!response.isCommitted()) {
            if (UrlUtil.isImage(request.getRequestURI())) {
                outImage(response, path, new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT));
//                response.setContentType(Constants.RESPONSE_CONTENT_TYPE_IMAGE);
//                ImageIO.write(ImageUtil.noImage(RequestUtil.getSize(request)),
//                        Constants.IMAGE_TYPE, response.getOutputStream());
//                response.setStatus(404);
                return;
            }
            if (RequestUtil.isAjax(request)) {

            }
            request.getRequestDispatcher(Constants.URL_404_ALL).forward(request, response);
        }
//        return false;
    }

    //    public static boolean outImage(HttpServletResponse response){
//
//    }

    /**
     * 输出图片
     * @param response
     * @param path
     * @param size
     * @throws IOException
     */

    public static void outImage( HttpServletResponse response, String path, Size size) throws IOException {
//        File file = new File(path);
//        response.setContentType(Constants.RESPONSE_CONTENT_TYPE_IMAGE);


        response.sendRedirect(path);








//        if (file.exists()) {
//            if (size == null || size.getWidth() <= 0 || size.getHeight() <= 0) {//没有高度的
//
//
//                ImageIO.write(ImageIO.read(file), Constants.IMAGE_TYPE, response.getOutputStream());
//            } else {
//                ImageIO.write(ImageUtil.sizeImage(path, size), Constants.IMAGE_TYPE, response.getOutputStream());
//            }
//        } else {
//            ImageIO.write(ImageUtil.noImage(new Size(Constants.DEFAULT_SIZE_WIDTH,
//                            Constants.DEFAULT_SIZE_HEIGHT)),
//                    Constants.IMAGE_TYPE, response.getOutputStream());
//        }
    }
}
