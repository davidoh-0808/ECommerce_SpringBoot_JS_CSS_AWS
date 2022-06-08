package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Category;
import com.application.gentlegourmet.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    ////////////////////////////////////////////////////////////////////////////////


    public Category findCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId).get();
    }

}
