package com.UTFPR.server.service;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.User;
import com.UTFPR.domain.entities.UserCategory;
import com.UTFPR.server.repository.CategoryRepository;
import com.UTFPR.server.repository.UserCategoryRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryService(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }


    public boolean isExistentRealtionshipUserCategory(UserCategory userCategory) {
        List<UserCategory> userCategories = userCategoryRepository.findUserCategoryByRelationshipUserCategory(userCategory);
        return !userCategories.isEmpty();
    }

    public UserCategory getRelationshipUserCategory(UserCategory userCategory) {
        List<UserCategory> userCategories = userCategoryRepository.findUserCategoryByRelationshipUserCategory(userCategory);
        return userCategories.isEmpty() ? null : userCategories.get(0);
    }

    @Transactional
    public void registerUserCategory(UserCategory userCategory) {
        userCategoryRepository.save(userCategory);
    }
//
//
//    public Category getCategoryById(int id) {
//        List<Category> categories = categoryRepository.findCategoryById((long) id);
//        return categories.isEmpty() ? null : categories.get(0);
//    }
//
    public List<Integer> getAllCategoriesByUser(User user) {
        List<UserCategory> userCategories = userCategoryRepository.listAllCategoriesByUser(user);
        List<Integer> idsCategories = new ArrayList<>();

        for(UserCategory userCategory : userCategories){
            idsCategories.add((int) userCategory.getCategory().getId());
        }

        return idsCategories.isEmpty() ? null : idsCategories;
    }

    @Transactional
    public void deleteUserCategory(UserCategory userCategory){
        userCategoryRepository.deleteUserCategory(userCategory);
    }

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
