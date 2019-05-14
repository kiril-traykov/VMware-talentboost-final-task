package com.talentboost.api.api.ServicesImpl;


import com.talentboost.api.api.Models.Domain;
import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Services.DomainServiceInterface;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService implements DomainServiceInterface {

    private String url = "https://meme-it-platform-service-api.herokuapp.com/domain";
    private RestTemplate rest;
    private Integer index;
    private Domain[] domains;
    private Meme[] memes;

    public DomainService() {
        rest = new RestTemplate();
    }

    @Override
    @PostConstruct
    public void register(){
        String serverName = "Normie Memes";
        String myAddress = "http://192.168.1.5:8080";
        Domain myDomain = new Domain(serverName, myAddress);
        try {
            index = rest.postForObject
                    (url + "/register?name=" + serverName + "&address=" + myAddress, myDomain, Integer.class);
        }catch (Exception e){
            System.out.println("Could not Register");
            e.printStackTrace();
        }
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

    @Override
    public List<Meme> getFilteredMemes(String filter) {
          List<Meme> filteredMemes = new ArrayList<>();
          for(Meme meme : memes){
              if(meme.getTitle().toLowerCase().contains(filter.toLowerCase())){
                  filteredMemes.add(meme);
              }
          }
          if(filteredMemes.size() == 0){
              filteredMemes.add(findClosestMeme(filter));
          }

        return filteredMemes;
    }

    private Meme findClosestMeme(String filter) {

        int minLevenStheinDistance = 123123123; // just need a very big number
        int currentDistance;
        Meme tmp = null;
        for (Meme meme : memes) {
            currentDistance = levenStheinAlgorithm(meme.getTitle(), filter);
            if (currentDistance < minLevenStheinDistance) {
                minLevenStheinDistance = currentDistance;
                tmp = meme;
            }
        }

     return tmp;
    }

    private int levenStheinAlgorithm(String title,String filter) {
        int[][] dp = new int[title.length() + 1][filter.length() + 1];


        for(int i = 0; i <= filter.length();i++){
            dp[0][i] = i;
        }
        for(int i = 0; i <= title.length();i++){
            dp[i][0] = i;
        }
        for(int i = 1; i <= title.length();i++){
            for(int j = 1; j <= filter.length();j++){
                dp[i][j] = Math.min(dp[i - 1][j - 1] + areEqual(title.charAt(i - 1), filter.charAt(j - 1)),Math.min(dp[i - 1][j] + 1,dp[i][j - 1] + 1));
            }
        }

        return dp[title.length()][filter.length()];
    }

    private int areEqual(char a, char b) {
        if(a == b)return 0;
        return 1;
    }


    @PreDestroy
    @Override
    public void onDestroy() throws Exception {
        rest.delete(url + "/deregister/" + index);
    }



}
