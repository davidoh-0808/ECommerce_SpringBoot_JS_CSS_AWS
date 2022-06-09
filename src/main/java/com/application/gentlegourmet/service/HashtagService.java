package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Hashtag;
import com.application.gentlegourmet.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;


    /////////////////////////////////////////////////////////////////////////////////////////////


    public void updateHashtagOnSearch(String keyword) {
        Hashtag hashtagFromDB = hashtagRepository.findHashtagByKeyword(keyword);
        System.out.println(hashtagFromDB);
        if(hashtagFromDB != null) {
            //increase the searched column count
            int previousSearched = hashtagFromDB.getSearched();
            hashtagFromDB.setSearched( previousSearched + 1 );
            hashtagRepository.save(hashtagFromDB);
        } else {
            Hashtag newHashtag = new Hashtag();
            newHashtag.setKeyword(keyword);
            newHashtag.setSearched(1);
            hashtagRepository.save(newHashtag);
        }

    }


    public List<Hashtag> getTopFiveSearchedHashtags() {
        List<Hashtag> hashtagList = hashtagRepository.getHashtagsOrderBySearched();

        //filter top 5 << but instead of this.. use Pageable or .setMaxResult() that JPA provides to limit queried rows
        return hashtagList.subList(0, 5);
    }


}
