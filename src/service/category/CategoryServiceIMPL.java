package service.category;

import config.Config;
import model.Category;
import model.Video;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceIMPL implements ICategoryService {
    List<Category> categoryList = new Config<Category>().readFromFile(Config.PATH_CATEGORY);
    List<Video> videoList = new Config<Video>().readFromFile(Config.PATH_VIDEO);

    @Override
    public List<Category> findAll() {
        return categoryList;
    }

    @Override
    public void save(Category category) {
        if (findById(category.getId()) == null) {
            categoryList.add(category);
        } else {
            categoryList.set(categoryList.indexOf(findById(category.getId())), category);
        }
        new Config<Category>().writeToFile(Config.PATH_CATEGORY, categoryList);
    }

    @Override
    public Category findById(int id) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == id) {
                return categoryList.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        int index = categoryList.indexOf(findById(id));
        categoryList.remove(index);
        new Config<Category>().writeToFile(Config.PATH_CATEGORY, categoryList);
    }

    @Override
    public boolean existByName(String name) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    @Override
    public List<Video> showListVideoByName(String name) {
        List<Video> searchList = new ArrayList<>();
        for (Video video : videoList) {
            if (video.getCategory().getName().equalsIgnoreCase(name))
                searchList.add(video);
        }
        return searchList;
    }
}
