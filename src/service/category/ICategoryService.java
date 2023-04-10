package service.category;

import model.Category;
import model.Video;
import service.IGenericService;

import java.util.List;

public interface ICategoryService extends IGenericService<Category> {
    boolean existByName(String name);
    List<Video> showListVideoByName(String name);
}
