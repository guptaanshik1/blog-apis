package com.blogapis.blogapi.service;

import com.blogapis.blogapi.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public CategoryDTO createCategory(CategoryDTO categoryDTO);

    public CategoryDTO getCategory(Integer categoryId);

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    public void deleteCategory(Integer categoryId);

    public List<CategoryDTO> getCategories();

}
