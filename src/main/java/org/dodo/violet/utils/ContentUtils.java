package org.dodo.violet.utils;

public class ContentUtils {

    public static String getIntroduction(String html){
        String txtcontent = html.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        if(txtcontent.length()>600)
            txtcontent = txtcontent.substring(0,300);
        return txtcontent+"......";
    }
}
