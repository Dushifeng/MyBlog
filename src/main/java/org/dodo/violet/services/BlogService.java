package org.dodo.violet.services;

import org.dodo.violet.dao.ArticleContentRepository;
import org.dodo.violet.dao.ArticleInfoRepositoty;
import org.dodo.violet.dao.CommentRepository;
import org.dodo.violet.dao.SortInfoRepository;
import org.dodo.violet.entities.ArticleContent;
import org.dodo.violet.entities.ArticleInfo;
import org.dodo.violet.entities.SortInfo;
import org.dodo.violet.utils.ContentUtils;
import org.dodo.violet.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    ArticleContentRepository articleContentRepository;
    @Autowired
    ArticleInfoRepositoty articleInfoRepositoty;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    SortInfoRepository sortInfoRepository;

    Logger logger;
    {
        logger= LoggerFactory.getLogger(BlogService.class);
    }




    public Page<ArticleInfo> getAllArticles(int pno,int size){
        Pageable pageable = PageRequest.of(pno-1, size);
        Page<ArticleInfo> all = articleInfoRepositoty.findAll(pageable);
        List<ArticleInfo> content = all.getContent();
        for(ArticleInfo info:content){
            String text = ContentUtils.getIntroduction(info.getArticleContent().getContent());
            info.setIntroduction(text);
        }
        return all;
    }




    public void saveArticle(String title, ArrayList<SortInfo> sortInfo, String content) {
        ArticleContent articleContent = new ArticleContent(content);

        ArticleInfo articleInfo = new ArticleInfo(title,new Date(),0,articleContent,sortInfo);
        if(sortInfo==null){
            sortInfo = new ArrayList<>();
        }
        for(SortInfo sort:sortInfo){

            sort.getArticleInfos().add(articleInfo);
        }
        articleInfoRepositoty.save(articleInfo);
        articleContentRepository.save(articleContent);
        sortInfoRepository.saveAll(sortInfo);
    }

    public Optional<ArticleInfo> getArticle(int id) {
        Optional<ArticleInfo> ans = articleInfoRepositoty.findById(id);
        return ans;
    }

    public void deleteArticle(int id) {
        articleInfoRepositoty.deleteById(id);
    }

    public void upDateArticle(int id, String title, ArrayList<SortInfo> sortInfos, String content) {
        Optional<ArticleInfo> _articleInfo = articleInfoRepositoty.findById(id);
        ArticleInfo articleInfo = _articleInfo.get();
        articleInfo.setTitle(title);
        ArticleContent articleContent = articleInfo.getArticleContent();
        articleContent.setContent(content);

        List<SortInfo> sorts = articleInfo.getSortInfos();
        for (SortInfo sortInfo:sorts){
            sortInfo.getArticleInfos().remove(articleInfo);
        }

        for(SortInfo sortInfo:sortInfos){
            sortInfo.getArticleInfos().add(articleInfo);
        }

        articleInfo.setSortInfos(sortInfos);
        articleInfoRepositoty.save(articleInfo);
        articleContentRepository.save(articleContent);

        sortInfoRepository.saveAll(sortInfos);
        sortInfoRepository.saveAll(sorts);
    }

    public List<SortInfo> getAllSort() {
       return sortInfoRepository.findAll();
    }

    public void saveSortInfo(String name) {
        SortInfo sortInfo = new SortInfo();
        sortInfo.setName(name);
        sortInfo.setCreateTime(new Date());
        sortInfoRepository.save(sortInfo);
    }

    public void deleteSort(int id) {
        sortInfoRepository.deleteById(id);
    }

    public void updateSort(int id, String name) {
        SortInfo one = sortInfoRepository.findById(id).get();
        one.setName(name);
        sortInfoRepository.save(one);
    }
}
