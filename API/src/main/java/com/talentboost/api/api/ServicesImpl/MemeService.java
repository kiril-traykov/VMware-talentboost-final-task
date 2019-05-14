package com.talentboost.api.api.ServicesImpl;

import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Repository.Repository;
import com.talentboost.api.api.Services.MemeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@Component
public class MemeService implements MemeServiceInterface {

    @Autowired
    Repository repository;
    private String imagesPath = "C:\\MemeWorld\\Images\\";
    private String serverAddress = "http://192.168.1.5:8080";
    private String filter = "";
    private int contentPerPage;
    private int totalPagesFilter;
    @Override
    public List<Meme> getAllMemes() {

        List<Meme> allMemes = repository.findAll();
        allMemes.sort(new SortbyId());
        return allMemes;
    }

    @Override
    public List<Meme> getPagedMemes(int page) {
        if(filter.equals("")){
            return getPagedMemesNoFilter(page);
        }
        return getPagedMemesFilter(page);
    }

    private List<Meme> getPagedMemesFilter(int page) {
        List<Meme> allMemes = repository.findAll();
        allMemes.sort(new SortbyId());
        List<Meme> selectedMemes = new ArrayList<>();
        List<Meme> allFilteredMemes = new ArrayList<>();
        for(int i = 0; i < allMemes.size();i++){
            if(allMemes.get(i).getTitle().toLowerCase().contains(filter.toLowerCase())){
                allFilteredMemes.add(allMemes.get(i));
            }
        }
        totalPagesFilter = (int)Math.ceil((float)allFilteredMemes.size()/(float)contentPerPage);
        for(int i = (page - 1) * contentPerPage; i < page * contentPerPage && i < allFilteredMemes.size();i++) {
            selectedMemes.add(allFilteredMemes.get(i));
        }

        System.out.println(true);

        return selectedMemes;
    }

    private List<Meme> getPagedMemesNoFilter(int page) {
        List<Meme> allMemes = (List<Meme>) repository.findAll();
        allMemes.sort(new SortbyId());
        List<Meme> selectedMemes = new ArrayList<>();
        for(int i = (page - 1) * contentPerPage; i < page * contentPerPage && i < allMemes.size();i++) {
           selectedMemes.add(allMemes.get(i));
        }
        return selectedMemes;
    }

    @Override
    public int getTotalPages(int contentPerPage) {
        this.contentPerPage = contentPerPage;
        if(filter.equals("")){
            int totalPages = (int)Math.ceil((float)repository.count()/(float)contentPerPage);
            if(totalPages == 0){
                return 1;
            }
        return totalPages;
        }
        else{
            if(totalPagesFilter == 0){
                return 1;
            }
            return totalPagesFilter;
        }
    }

    @Override
    public void addMeme(MultipartFile file) {
        String title = file.getOriginalFilename();
        Path path = Paths.get(imagesPath + title + ".png");
        try {
            byte[] arr = file.getBytes();
            Files.write(path, arr);
        } catch (IOException e) {
            System.out.println("Upload failed");
            e.printStackTrace();
        }
        String url = serverAddress + "/images/" + title + ".png";
        repository.save(new Meme(title, url));
        removeFilter();
    }

    @Override
    public void deleteMeme(int index) {
     repository.deleteById(index);
        removeFilter();
    }

    @Override
    public void updateMeme(MultipartFile file, int id) throws IOException, NoSuchFieldException {
        String newTitle = file.getOriginalFilename();
        Path path = Paths.get(imagesPath + newTitle + ".png");

        try {
            byte[] arr = file.getBytes();
            Files.write(path, arr);
        } catch (IOException e) {
            System.out.println("Image uploading failed");
            e.printStackTrace();
        }

        String url = serverAddress + "/images/" + newTitle + ".png";
        Meme meme = repository.findById(id).orElse(null);
        if(meme != null){
            meme.setTitle(newTitle);
        meme.setUrl(url);
        repository.save(meme);
        removeFilter();
        }
        else{
            throw new NoSuchFieldException();
        }
    }

    @Override
    public void updateMemeTitle(String newTitle, int id) throws NoSuchFieldException {
        Meme meme = repository.findById(id).orElse(null);
        if(meme != null){
            meme.setTitle(newTitle);
        repository.save(meme);
        removeFilter();
        }
        else{
            throw new NoSuchFieldException();
        }
    }

    @Override
    public void setFilter(String filter) {
     if(filter.equals("RemoveFilterFromApi")){
                   removeFilter();
     }
     else{
     this.filter = filter;
     }
    }
    private void removeFilter(){
        this.filter = "";
    }
}

class SortbyId implements Comparator<Meme>
{
    public int compare(Meme a, Meme b)
    {
        return b.getId() - a.getId();
    }
}
