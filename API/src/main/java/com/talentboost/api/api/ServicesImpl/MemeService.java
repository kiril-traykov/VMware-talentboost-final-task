package com.talentboost.api.api.ServicesImpl;

import com.talentboost.api.api.Application;
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
    private String imagesPath = "../Images/";
    private String filter = "";
    private int contentPerPage;
    private int totalPagesFilter;

    @Override
    public List<Meme> getAllMemes() {

        List<Meme> allMemes = repository.findAll();
        allMemes.sort(new SortbyId());
        return mapAddressToImageUrl(allMemes);
    }

    @Override
    public List<Meme> getPagedMemes(int page) {
        List<Meme> memes;
        if(filter.equals("")){
            memes = getPagedMemesNoFilter(page);
        }
        else {
            memes =  getPagedMemesFilter(page);
        }

        return mapAddressToImageUrl(memes);
    }

    @Override
    public int getTotalPages(int contentPerPage) {

        this.contentPerPage = contentPerPage;

        if(filter.equals(""))
        {
            int totalPages = (int)Math.ceil((float)repository.count()/(float)contentPerPage);
            if(totalPages == 0){
                return 1;
            }
        return totalPages;
        }
        else
            {
            if(totalPagesFilter == 0){
                return 1;
            }
            return totalPagesFilter;
        }
    }

    @Override
    public void addMeme(MultipartFile file) {
        String title = file.getOriginalFilename();
        String image = "/images/" + title + ".png";
        Path path = Paths.get(imagesPath + title + ".png");

        try
        {
            byte[] arr = file.getBytes();
            Files.write(path, arr);
        } catch (IOException e) {
            System.out.println("Upload failed");
            e.printStackTrace();
        }

        repository.save(new Meme(title, image));
        removeFilter();
    }

    @Override
    public void deleteMeme(int index) {
     repository.deleteById(index);
     removeFilter();
    }

    @Override
    public void updateMeme(MultipartFile file, int id) throws NoSuchFieldException {
        String newTitle = file.getOriginalFilename();
        String image =  "/images/" + newTitle + ".png";
        Path path = Paths.get(imagesPath + newTitle + ".png");
        Meme meme;

        try
        {
            byte[] arr = file.getBytes();
            Files.write(path, arr);
        } catch (IOException e) {
            System.out.println("Image uploading failed");
            e.printStackTrace();
        }

        meme = repository.findById(id).orElse(null);
        if(meme != null)
        {
            meme.setTitle(newTitle);
            meme.setImage(image);
            repository.save(meme);
        }
        else{
            throw new NoSuchFieldException();
        }
        removeFilter();
    }

    @Override
    public void updateMemeTitle(String newTitle, int id) throws NoSuchFieldException {
        Meme meme = repository.findById(id).orElse(null);

        if(meme != null){
            meme.setTitle(newTitle);
            repository.save(meme);
        }
        else{
            throw new NoSuchFieldException();
        }
        removeFilter();
    }

    @Override
    public void setFilter(String filter) {
     if(filter.equals("RemoveFilterFromApi"))
     {
         removeFilter();
     }
     else
     {
     this.filter = filter;
     }
    }

    private List<Meme> getPagedMemesFilter(int page) {
        List<Meme> allMemes = repository.findAll();
        List<Meme> selectedMemes = new ArrayList<>();
        List<Meme> allFilteredMemes = new ArrayList<>();
        allMemes.sort(new SortbyId());

        for(int i = 0; i < allMemes.size();i++){
            if(allMemes.get(i).getTitle().toLowerCase().contains(filter.toLowerCase())){
                allFilteredMemes.add(allMemes.get(i));
            }
        }

        totalPagesFilter = (int)Math.ceil((float)allFilteredMemes.size()/(float)contentPerPage);

        for(int i = (page - 1) * contentPerPage; i < page * contentPerPage && i < allFilteredMemes.size();i++) {
            selectedMemes.add(allFilteredMemes.get(i));
        }

        return selectedMemes;
    }

    private List<Meme> getPagedMemesNoFilter(int page) {
        List<Meme> allMemes = repository.findAll();
        List<Meme> selectedMemes = new ArrayList<>();
        allMemes.sort(new SortbyId());

        for(int i = (page - 1) * contentPerPage; i < page * contentPerPage && i < allMemes.size();i++) {
            selectedMemes.add(allMemes.get(i));
        }
        return selectedMemes;
    }

    private void removeFilter()
    {
        this.filter = "";
    }

    private List<Meme> mapAddressToImageUrl(List <Meme> memes){

        for(Meme m : memes){
            m.setImage(Application.myServerAddress + m.getImage());
        }
        return memes;
    }
}

class SortbyId implements Comparator<Meme>
{
    public int compare(Meme a, Meme b)
    {
        return b.getId() - a.getId();
    }
}
