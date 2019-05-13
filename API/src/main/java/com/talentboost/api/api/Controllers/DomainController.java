package com.talentboost.api.api.Controllers;

import com.talentboost.api.api.Models.Domain;
import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Services.DomainServiceInterface;
import com.talentboost.api.api.ServicesImpl.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
