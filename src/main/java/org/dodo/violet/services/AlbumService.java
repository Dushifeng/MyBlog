package org.dodo.violet.services;

import org.dodo.violet.dao.AlbumRespository;
import org.dodo.violet.dao.UpFileRepository;
import org.dodo.violet.entities.Album;
import org.dodo.violet.entities.UpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    AlbumRespository albumRespository;
    @Autowired
    UpFileRepository upFileRepository;

    public List<Album> getAllAlbums(){
        return albumRespository.findAll();
    }


    public void addAlbum(String name, String introduction) {
        Album album = new Album(name,introduction,new Date());
        albumRespository.save(album);
    }

    public Album getAlbum(Integer aid) {
        return albumRespository.findById(aid).get();
    }

    public List<UpFile> upFile(Integer aid, List<UpFile> fileList) {
        Album album = albumRespository.findById(aid).get();
        album.setPhotos(fileList);
        for(UpFile file:fileList){
            file.setAlbum(album);
        }
        List<UpFile> files = upFileRepository.saveAll(fileList);
        return files;
    }

    public UpFile getFile(int id) {
        return upFileRepository.findById(id).get();
    }

    public void updateDetail(int id, String name, String introduction) {
        UpFile one = upFileRepository.findById(id).get();
        one.setName(name);
        one.setIntroduction(introduction);
        upFileRepository.save(one);

    }

    public void deleteAlbum(int id) {
        albumRespository.deleteById(id);
    }

    public void updateAlbum(int id, String name, String introduction) {

        Album album = albumRespository.findById(id).get();
        album.setName(name);
        album.setIntroduction(introduction);
        albumRespository.save(album);
    }
}
