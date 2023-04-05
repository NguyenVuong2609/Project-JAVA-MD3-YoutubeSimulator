package service.category;

import model.Category;
import service.IGenericService;

public interface ICategoryService extends IGenericService<Category> {
    boolean existByName(String name);
}
