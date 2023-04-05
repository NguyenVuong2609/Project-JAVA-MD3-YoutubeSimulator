package controller;

import dto.response.ResponseMessage;
import model.Category;
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
}
