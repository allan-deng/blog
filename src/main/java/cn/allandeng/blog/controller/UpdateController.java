package cn.allandeng.blog.controller;/**
 * @Auther: Allan
 * @Date: 2020/2/8 23:58
 * @Description:
 */

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName UpdateController
 * @Date:2020/2/8 23:58
 * @Description: 文件上传控制器
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Controller
public class UpdateController {

    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * @Title uploadImg
     * @Author Allan Deng
     * @Description  上传图片
     * @Date 13:24 2020/2/10
     * @Param [request, response, attach]
     * @return void
     **/
    @PostMapping(value="/uploadfile")
    public void uploadImg(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach){
        try {
            request.setCharacterEncoding( "utf-8" );
            response.setHeader( "Content-Type" , "text/html" );
            //String rootPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
            String rootPath = uploadPath+"resources";

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath=new File(rootPath);
            if(!filePath.exists()){
                filePath.mkdirs();
            }

            //最终文件名
            File realFile=new File(rootPath+File.separator+attach.getOriginalFilename());
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);

            //下面response返回的json格式是editor.md所限制的，规范输出就OK
            response.getWriter().write( "{\"success\": 1, \"message\":\"上传成功\",\"url\":\"/resources/" + attach.getOriginalFilename() + "\"}" );
        } catch (Exception e) {
            try {
                response.getWriter().write( "{\"success\":0}" );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
