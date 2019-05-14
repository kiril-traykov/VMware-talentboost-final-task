package com.talentboost.api.api;

import com.talentboost.api.api.Models.Domain;
import com.talentboost.api.api.Models.Meme;
import com.talentboost.api.api.Repository.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@DataJpaTest
public class MemeServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private String url = "http://localhost:8080";


    @Autowired
    TestEntityManager entityManager;
    @Autowired

    JpaRepository<Meme,Integer> repository;


    @Test
    public void TestGetAllMemes(){
        restTemplate = new TestRestTemplate();
        Domain[] response = restTemplate.getForObject(url + "/memes",Domain[].class);
        assertNotNull(response);
    }

    @Test
    public void createMeme(){
        Meme tmp = new Meme("Test Meme","Test Meme");
        entityManager.persist(tmp);
        Meme ansEntity = repository.findById(tmp.getId()).orElse(null);
        assertEquals(tmp, ansEntity);
    }

    @Test
    public void should_delete_meme() {
        Meme tmp = new Meme("Test Meme","Test Meme");
        entityManager.persist(tmp);
        int id = tmp.getId();
        repository.delete(tmp);
        Meme ansEntity = repository.findById(id).orElse(null);
        assertNull(ansEntity);
    }



}



