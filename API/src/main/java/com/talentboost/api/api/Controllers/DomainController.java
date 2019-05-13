package com.talentboost.api.api.Controllers;

import com.talentboost.api.api.Models.Domain;
import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Services.DomainServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class DomainController {

    @Autowired
    DomainServiceInterface domainService;

    @GetMapping("/domains")
    public Domain[] getDomains(){
        return domainService.getDomains();
    }

    @GetMapping("/OtherMemes/{domain}")
    public Meme[] getMemes(@PathVariable String domain){
        return domainService.getMemes(domain);
    }

    @GetMapping("/filter/{filter}")
    public List<Meme> getFilteredMemes(@PathVariable String filter){
        return domainService.getFilteredMemes(filter);
    }

}
