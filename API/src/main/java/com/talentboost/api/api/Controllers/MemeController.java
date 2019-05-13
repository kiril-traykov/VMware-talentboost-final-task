package com.talentboost.api.api.Controllers;

import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Services.MemeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@CrossOrigin
@RestController
public class MemeController {

    @Autowired
    MemeServiceInterface memeService;

    @GetMapping("/memes")
        public List<Meme> returnAllMemes(){
        return memeService.getAllMemes();
    }

    @GetMapping("/memes/{page}")
        public List<Meme> returnPagedMemes(@PathVariable int page){
        System.out.println("GotPagedMemes");
        return memeService.getPagedMemes(page);

    }

    @GetMapping("/page/{contentPerPage}")
    public int getTotalPages(@PathVariable int contentPerPage){
    return memeService.getTotalPages(contentPerPage);
    }

    @PostMapping("/add")
    public void addMeme(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Adding meme");
    memeService.addMeme(file);

    }

    @DeleteMapping("/delete/{index}")
    public void delete(@PathVariable int index){
    memeService.deleteMeme(index);

    }

    @PutMapping("/update/{id}")
    public void update(@RequestParam("file") MultipartFile file,@PathVariable int id) throws IOException {
    memeService.updateMeme(file,id);

    }
    @PutMapping("/updateTitle/{id}")
    public void update(@RequestBody String newTitle,  @PathVariable int id) {
    memeService.updateMemeTitle(newTitle,id);

    }

    @PostMapping("/filter")
    public void setFilter(@RequestBody String filter){
    memeService.setFilter(filter);

    }

}
