package com.UTFPR.server.service;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.server.repository.CategoryRepository;
import com.UTFPR.server.repository.UserCategoryRepository;
import jakarta.transaction.Transactional;

import java.util.List;

public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryService(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }


//    public boolean isExistentCategory(Category category) {
//        List<Category> categories = categoryRepository.findCategoryById(category.getId());
//        return !categories.isEmpty();
//    }
//
//    @Transactional
//    public void registerCategory(Category category) {
//        categoryRepository.save(category);
//    }
//
//
//    public Category getCategoryById(int id) {
//        List<Category> categories = categoryRepository.findCategoryById((long) id);
//        return categories.isEmpty() ? null : categories.get(0);
//    }
//
//    public List<Category> getAllCategories() {
//        List<Category> categories = categoryRepository.listAllCategories();
//        return categories.isEmpty() ? null : categories;
//    }
//
//    @Transactional
//    public void deleteCategory(Category category){
//        categoryRepository.deleteCategory(category);
//    }
//
//    @Transactional
//    public void editCategoryById(int id, Category categoryEdited){
//        categoryRepository.editCategoryById((long)id, categoryEdited);
//    }
//
//    public boolean isPresentOnWarnings(int id) {
////        List<Category> categories = categoryRepository.isPresentOnWarning(id);
////        return !categories.isEmpty();
//        return false;
//    }
}
