package org.dodo.violet.utils;

import org.dodo.violet.entities.UpFile;
import org.dodo.violet.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtils {

    @Autowired
    AlbumService albumService;

//    //文件上传工具类服务方法
//    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{
//
//        File targetFile = new File(filePath);
//        if(!targetFile.exists()){
//            targetFile.mkdirs();
//        }
//        FileOutputStream out = new FileOutputStream(filePath+fileName);
//        out.write(file);
//        out.flush();
//        out.close();
//    }

    public static UpFile uploadImage(HttpServletRequest request, String path_deposit, MultipartFile file, boolean isRandomName) {
        //上传
        UpFile upFile = null;
        try {
            String[] typeImg={"gif","png","jpg","bmp","tif","pcx","tga","svg"};

            if(file!=null){
                String origName=file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:"+origName);
                // 判断文件类型
                String type=origName.indexOf(".")!=-1?origName.substring(origName.lastIndexOf(".")+1, origName.length()):null;
                if (type!=null) {
                    upFile = new UpFile();
                    upFile.setName(origName);
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            upFile.setPhoto(true);
                        }
                    }

                    //存放图片文件的路径
//                    String path="/root/projectData";
                    String path = "D:/test";
                    System.out.println("文件上传的路径"+path);
                    //组合名称
                    String fileSrc = path+path_deposit;
                    //是否随机名称
                    if(isRandomName){
                        //随机名规则：文件名+_CY+当前日期+8位随机数+文件后缀名
                        origName=origName.substring(0,origName.lastIndexOf("."))+"_CY"+formateString(new Date())+
                                MathUtils.getRandom620(8)+origName.substring(origName.lastIndexOf("."));
                    }

                    System.out.println("随机文件名："+origName);
                    upFile.setShowPath("/upfile/"+path_deposit+origName);
                    //判断是否存在目录
                    File targetFile=new File(fileSrc,origName);
                    if(!targetFile.exists()){
                        targetFile.getParentFile().mkdirs();//创建目录
                    }

                    //上传
//                    file.transferTo(targetFile);

                    org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
                    //完整路径
                    System.out.println("完整路径:"+targetFile.getAbsolutePath());
                    upFile.setCreateTime(new Date());
                    upFile.setSrcPath(targetFile.getAbsolutePath());
                    return upFile;

                }
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期并去掉”-“
     * @param date
     * @return
     */
    public static String formateString(Date date){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String list[] = dateFormater.format(date).split("-");
        String result = "";
        for (int i=0;i<list.length;i++) {
            result += list[i];
        }
        return result;
    }


}