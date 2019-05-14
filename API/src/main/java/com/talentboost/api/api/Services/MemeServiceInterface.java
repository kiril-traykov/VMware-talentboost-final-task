package com.talentboost.api.api.Services;

import com.talentboost.api.api.Models.Meme;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface MemeServiceInterface {
    List<Meme> getAllMemes();

    List<Meme> getPagedMemes(int page);

    int getTotalPages(int contentPerPage);

    void addMeme(MultipartFile file) throws IOException;

    void deleteMeme(int index);

    void updateMeme(MultipartFile file, int id) throws IOException, NoSuchFieldException;

    void updateMemeTitle(String newTitle, int id) throws NoSuchFieldException;

    void setFilter(String filter);
}
