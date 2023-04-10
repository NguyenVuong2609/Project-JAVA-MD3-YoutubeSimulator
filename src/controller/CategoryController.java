package controller;

import dto.response.ResponseMessage;
import model.Category;
import model.Video;
import service.category.CategoryServiceIMPL;
import service.category.ICategoryService;

import java.util.List;

public class CategoryController {
    ICategoryService categoryService = new CategoryServiceIMPL();

    public List<Category> getCategoryList(){
        return categoryService.findAll();
    }
    public ResponseMessage createCategory(Category category){
        if (categoryService.existByName(category.getName()))
            return new ResponseMessage("category_existed");
        categoryService.save(category);
        return new ResponseMessage("create_success");
    }
    public Category findCategoryById(int id){
        return categoryService.findById(id);
    }
    public void deleteCategoryById(int id){
        categoryService.deleteById(id);
    }
    public void updateCategory(Category category){
        categoryService.save(category);
    }
    public boolean existByName(String name){
        return categoryService.existByName(name);
    }
    public List<Video> showListVideoByCategoryName(String name){
        return categoryService.showListVideoByName(name);
    }
}
