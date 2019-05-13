package com.talentboost.api.api.Repository;
import com.talentboost.api.api.Models.Meme;
import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository <Meme,Integer> {

}
