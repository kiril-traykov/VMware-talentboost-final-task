package com.talentboost.api.api.ServicesImpl;


import com.talentboost.api.api.Models.Domain;
import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Services.DomainServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class DomainService implements DomainServiceInterface {

    String url = "https://meme-it-platform-service-api.herokuapp.com/domain";
    private RestTemplate rest;
    private String serverName = "Normie Memes";
    private String myAddress = "http://192.168.1.5:8080";
    Integer index;
    Domain[] domains;
    Meme[] memes;

    public DomainService() {
        rest = new RestTemplate();
    }

    @Override
    @PostConstruct
    public void register(){
        Domain myDomain = new Domain(serverName,myAddress);
        index = rest.postForObject
                (url + "/register?name="+ serverName + "&address=" + myAddress,myDomain,Integer.class);
    }
    @Override

   public Domain[] getDomains(){
                domains = rest.getForObject(url,Domain[].class);
                return domains;
    }

    @Override
    public Meme[] getMemes(String domain){
        String address = "";
        for(Domain currentDomain : domains){
            if(currentDomain.getName().equals(domain)){
                address = currentDomain.getAddress();
                break;
            }
        }
         memes = rest.getForObject(address + "/memes",Meme[].class);
       return memes;
    }

    @PreDestroy
    @Override
    public void onDestroy() throws Exception {
        rest.delete(url + "/deregister/" + index);
    }

}
