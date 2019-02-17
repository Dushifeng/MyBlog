package org.dodo.violet.controllers;

import com.sun.org.apache.regexp.internal.RE;
import org.dodo.violet.config.DefaultConfig;

import org.dodo.violet.entities.Album;
import org.dodo.violet.entities.ArticleInfo;
import org.dodo.violet.services.AlbumService;
import org.dodo.violet.services.BlogService;
import org.dodo.violet.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
public class CommonController {

    @Autowired
    BlogService blogService;
    @Autowired
    AlbumService albumService;

    @Resource
    private ResourceLoader resourceLoader;


    @GetMapping("/test")
    public String test(HttpServletRequest req){
        File file = new File("src/main/resources/static");
        System.out.println(file.getAbsolutePath());
        return "common/test";
    }


    @GetMapping("/album/{id}")
    public String lookalbum(@PathVariable Integer id,Model model){
        Album album = albumService.getAlbum(id);
        model.addAttribute("album",album);
        return "common/photos";
    }


    @GetMapping("/albums")
    public String albumPage(Model model){
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("albums",albums);
        return "common/album";
    }


    @RequestMapping("showimg")
    public ResponseEntity showPhotos() {

        try {

            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file://" + "D:/p2.png" ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/")
    public String index(){
        return "common/index";
    }



    @GetMapping({"/articles/{pno}","/articles"})
    public String blogPage(Model model, @PathVariable(name = "pno",required = false) Integer pno){
        pno = pno==null? DefaultConfig.pno:pno;
        int size = DefaultConfig.size;
        Page<ArticleInfo> allArticles = blogService.getAllArticles(pno, size);
        model.addAttribute("articles",allArticles);
        return "common/blog";
    }

    @GetMapping("/article/{id}")
    public String readArticle(Model model,@PathVariable(name = "id") int id){
        Optional<ArticleInfo> article = blogService.getArticle(id);
        if(article.get()==null){
            //error page
        }
        model.addAttribute("article",article.get());
        return "common/articlePage";
    }

}
