package com.talentboost.api.api;

import com.talentboost.api.api.Models.Domain;
import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Repository.Repository;
import com.talentboost.api.api.Services.MemeServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class MemeServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private String url = "http://localhost:8080";


    @Autowired
    TestEntityManager entityManager;
    @Autowired
    Repository repository;


    @Test
    public void TestGetAllMemes(){
        restTemplate = new TestRestTemplate();
        Domain[] response = restTemplate.getForObject(url + "/memes",Domain[].class);
        assertNotNull(response);
    }


}
