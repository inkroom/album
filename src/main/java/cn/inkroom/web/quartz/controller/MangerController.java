package cn.inkroom.web.quartz.controller;

import cn.inkroom.web.quartz.config.PathConfig;
import cn.inkroom.web.quartz.service.ManagerService;

import org.springframework.beans.factory.annotation.Autowired;

//@Controller
public class MangerController extends BaseController {
    @Autowired
    private PathConfig pathConfig;
    @Autowired
    private ManagerService managerService;

//    @RequestMapping(value = {"/manager"}, method = {RequestMethod.GET})
//    public String toManger() {
//        return "manager";
//    }
//
//    @RequestMapping(value = {"/init"}, method = {RequestMethod.GET}, produces = {Constants.RESPONSE_CONTENT_TYPE_JSON})
//    @ResponseBody
//    public String init() {
//        JSONObject object = JsonUtil.setStatus(Result.SUCCESS);
//        object.put("dirs", this.managerService.init());
//
//        return object.toString();
//    }
//
//    @RequestMapping(value = {"/removeDir"}, produces = {Constants.RESPONSE_CONTENT_TYPE_JSON})
//    @ResponseBody
//    public String removeDir(String paths) {
//        JSONObject object = JsonUtil.setStatus(Result.SUCCESS);
//        object.put("dirs", this.managerService.removeDir(paths));
//        return object.toString();
//    }
//
//    @RequestMapping(value = {"/check"}, method = {RequestMethod.GET})
//    @ResponseBody
//    public String checkUpload(String fileName, String path) {
//        File targetFile = new File(path, fileName);
//        if (targetFile.exists()) {
//            return JsonUtil.setStatus(Result.FILE_EXISTS).toString();
//        }
//        if (!targetFile.getParentFile().exists()) {
//            return JsonUtil.setStatus(Result.ALBUM_NOT_EXISTS).toString();
//        }
//        return JsonUtil.setStatus(Result.SUCCESS).toString();
//    }
//
//
//
//    @RequestMapping(value = {"/{path}/createDir"}, method = {RequestMethod.GET})
//    @ResponseBody
//    public String createDir(@PathVariable String path) {
//        File file = new File(this.pathConfig.getImageBasePath(), path);
//        if (file.exists()) {
//            return JsonUtil.setStatus(Result.DIR_EXISTS).toString();
//        }
//        return JsonUtil.setStatus(file.mkdir() ? Result.SUCCESS : Result.FAIL).toString();
//    }
//
//    @RequestMapping("upload")
//    public String toMessage(HttpServletRequest request) {
//        request.setAttribute("message", request.getAttribute("message"));
//        return "upload";
//    }
//
//    @ExceptionHandler()
//    public String exception(Throwable throwable) {
//        return new AjaxBean(Result.FAIL, throwable.getMessage()).toString();
//    }
}
