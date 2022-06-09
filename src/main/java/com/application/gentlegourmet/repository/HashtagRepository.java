package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    @Query("SELECT h FROM Hashtag h WHERE h.keyword = :keyword")
    Hashtag findHashtagByKeyword(@Param("keyword") String keyword);


    @Query("SELECT h FROM Hashtag h ORDER BY h.searched DESC")
    List<Hashtag> getHashtagsOrderBySearched();

    //also using jpa default query .save(hashtagFromDB) which does insert and update befittingly

}
