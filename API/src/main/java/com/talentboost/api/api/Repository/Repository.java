package com.talentboost.api.api.Repository;
import com.talentboost.api.api.Models.Meme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository <Meme,Integer> {

}
