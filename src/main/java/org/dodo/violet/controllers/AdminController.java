package org.dodo.violet.controllers;


import org.dodo.violet.config.DefaultConfig;
import org.dodo.violet.dao.SortInfoRepository;
import org.dodo.violet.entities.Album;
import org.dodo.violet.entities.ArticleInfo;
import org.dodo.violet.entities.SortInfo;
import org.dodo.violet.entities.UpFile;
import org.dodo.violet.services.AlbumService;
import org.dodo.violet.services.BlogService;
import org.dodo.violet.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    BlogService blogService;

    private int articlePno=1;

    @Autowired
    AlbumService albumService;




    @ResponseBody
    @PostMapping("/upDateDetail")
    public void postdetail(@RequestParam("id") Integer id,@RequestParam("name") String name,@RequestParam("introduction") String introduction){
        System.out.println(id+"  "+name+"  "+introduction);
        albumService.updateDetail(id,name,introduction);
    }




    @PostMapping("/detailPage")
    public String detailPage(String val,Model model){
        System.out.println(val);
        String[] split = val.split("A");
        List<UpFile> files = new ArrayList<>();
        for(String s:split){
            if(!s.equals("")){
                UpFile file = albumService.getFile(Integer.parseInt(s));
                files.add(file);
                System.out.println(file.getName());
            }
        }
        model.addAttribute("files",files);
        return "admin/fileDetail";
    }


    @PostMapping("/album")
    @ResponseBody
    public void addAlbum(String name,String introduction){
        albumService.addAlbum(name,introduction);
    }
    @PutMapping("/album")
    @ResponseBody
    public void updateAlbum(int id,String name,String introduction){
        albumService.updateAlbum(id,name,introduction);
    }

    @DeleteMapping("/album/{id}")
    public String deleteAlbum(@PathVariable int id){
        albumService.deleteAlbum(id);
        return "redirect:/admin/albums";
    }
    @GetMapping("/update")
    public String updateFile(Model model){
        List<Album> allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums",allAlbums);
        return "admin/update";
    }

    @GetMapping("/albums")
    public String albumPage(Model model){
        List<Album> allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums",allAlbums);
        return "admin/album";
    }

    @GetMapping("/sorts")
    public String sortsManage(Model model){
        List<SortInfo> allSort = blogService.getAllSort();
        model.addAttribute("sorts",allSort);
        return "admin/sortInfo";
    }

    @PostMapping("/sort")
    public String addSort(String name){
        blogService.saveSortInfo(name);
        return "redirect:/admin/sorts";
    }

    @PutMapping("/sort")
    @ResponseBody
    public void updateSort(@RequestParam("name") String name,@RequestParam("id") int id){
        blogService.updateSort(id,name);
    }

    @DeleteMapping("/sort/{id}")
    @ResponseBody
    public void deleteSort(@PathVariable("id") int id){
        blogService.deleteSort(id);
    }

    @PostMapping("/article")
    public String addArticle(String title, @RequestParam(name = "sortInfos",required = false) ArrayList<SortInfo> sortInfos, String content){
        blogService.saveArticle(title,sortInfos,content);

        return "redirect:/admin";
    }

    @GetMapping("/article")
    public String addArticlePage(Model model){
        List<SortInfo> allSort = blogService.getAllSort();
        model.addAttribute("sorts",allSort);
        return "admin/addArticle";
    }



    @GetMapping({"/articles","/articles/{pno}"})
    public String blogPage(@PathVariable(name = "pno",required = false) Integer pno,Model model){
        pno = pno==null? DefaultConfig.pno:pno;
        Page<ArticleInfo> allArticles = blogService.getAllArticles(pno, DefaultConfig.size);
        model.addAttribute("articles",allArticles);

        articlePno = pno;

        return "admin/blog";
    }




    @GetMapping("/article/{id}")
    public String lookBlog(@PathVariable(name = "id") int id,Model model){
        Optional<ArticleInfo> article = blogService.getArticle(id);
        List<SortInfo> sortInfos = blogService.getAllSort();
        ArticleInfo articleInfo = article.get();
        model.addAttribute("article",articleInfo);
        model.addAttribute("sorts",sortInfos);

        List<SortInfo> sorts = articleInfo.getSortInfos();
        List<Integer> infos = new ArrayList<>();
        for(SortInfo sort:sorts){
            infos.add(sort.getId());
        }
        model.addAttribute("selectedInfos",infos);

        return "admin/articleDetail";
    }





    @PutMapping("/article")
    public String updateArticle(int id,String title, @RequestParam("sortInfos") ArrayList<SortInfo> sortInfos, String content){
        blogService.upDateArticle(id,title,sortInfos,content);
        return "redirect:/admin/";
    }



    @DeleteMapping("/article/{id}")
    public String removeArticle(@PathVariable("id") int id){
        blogService.deleteArticle(id);
        return "redirect:/admin/articles/"+articlePno;
    }



    @GetMapping({"/main",""})
    public String main(Model model){

        return "admin/main";
    }

    @ResponseBody
    @PostMapping("/upData")
    public Map<String,Object> updateFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file,@RequestParam("alblum_id") Integer aid){

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status", 400);
        if(file!=null&&file.length>0){
            //组合image名称，“;隔开”
            List<UpFile> fileList =new ArrayList<UpFile>();
            PrintWriter out = null;
            try {
                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        //上传文件，随机名称，";"分号隔开
                        fileList.add(FileUtils.uploadImage(request, "/upload/"+ FileUtils.formateString(new Date())+"/", file[i], true));
                    }
                }

                //上传成功
                if(fileList!=null&&fileList.size()>0){
                    List<UpFile> files = albumService.upFile(aid, fileList);
                    System.out.println("上传成功！");
                    resultMap.put("status", 200);
                    resultMap.put("message", "上传成功！");
                    resultMap.put("upfile",files.get(0));
                }else {
                    resultMap.put("status", 500);
                    resultMap.put("message", "上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("status", 500);
                resultMap.put("message", "上传异常！");
            }
        }else {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
        }
        return resultMap;


    }


    @RequestMapping("/upimg")
    @ResponseBody
    public Map<String,String> upimg(HttpServletRequest req, @RequestParam MultipartFile file){
        UpFile f = FileUtils.uploadImage(req, "", file, true);
        Map<String,String> map = new HashMap<>();
        map.put("location",f.getShowPath());
        return map;
    }

}
