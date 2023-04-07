package view.admin;

import config.ColorConsole;
import config.Config;
import controller.CategoryController;
import dto.response.ResponseMessage;
import model.Category;

import java.util.List;

public class CategoryView {
    CategoryController categoryController = new CategoryController();
    List<Category> categoryList = categoryController.getCategoryList();

    public CategoryView() {
        while (true) {
            System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ CATEGORY MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
            System.out.printf("|" + "  1. %-89s" + "|\n", "Show Category List");
            System.out.printf("|" + "  2. %-89s" + "|\n", "Create New Category");
            System.out.printf("|" + "  3. %-89s" + "|\n", "Update Category");
            System.out.printf("|" + "  4. %-89s" + "|\n", "Delete Category");
            System.out.printf("|" + "  5. %-89s" + "|\n", "Category Details");
            System.out.printf("|" + "  6. %-89s" + "|\n", "Back");
            System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ CATEGORY MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
            System.out.println("Please enter your choice: ");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    showCategoryList();
                    break;
                case 2:
                    createCategory();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    showCategoryDetails();
                    break;
                case 6:
                    ProfileView.getProfileViewInstance();
                    break;
                default:
                    System.out.println(Config.OOA_ALERT);
            }
        }
    }

    public void showCategoryList() {
        while (true) {
            System.out.println("Category List");
            for (Category category : categoryList) {
                System.out.printf("ID: %d - Name: %s\n", category.getId(), category.getName());
            }
            System.out.println(Config.CONTINUE_BACK_MENU);
            String backMenu = Config.scanner().nextLine();
            if (backMenu.equalsIgnoreCase("back")) {
                new CategoryView();
                break;
            }
        }
    }

    public void createCategory() {
        int id;
        if (categoryList.size() == 0) {
            id = 1;
        } else {
            id = categoryList.get(categoryList.size() - 1).getId() + 1;
        }
        System.out.println("New Category");
        System.out.println("Enter the category's name: ");
        String name = Config.validateString();
        Category category = new Category(id, name);
        while (true) {
            ResponseMessage responseMessage = new CategoryController().createCategory(category);
            if (responseMessage.getMessage().equals("category_existed")) {
                System.err.println("Category existed!");
                System.out.println("Enter the category's name: ");
                name = Config.validateString();
                category.setName(name);
            } else {
                System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new CategoryView();
                break;
            }
        }
    }

    public void updateCategory() {
        System.out.println("Edit category");
        System.out.println("Category List");
        for (Category category : categoryList) {
            System.out.printf("ID: %d - Name: %s\n", category.getId(), category.getName());
        }
        System.out.println("Enter an ID that you want to edit: ");
        while (true) {
            int id = Config.validateInt();
            boolean flag = false;
            Category category = categoryController.findCategoryById(id);
            if (category != null) {
                System.out.println("Enter a new category name: ");
                String name = Config.validateString();
                category.setName(name);
                while (true) {
                    ResponseMessage responseMessage = new CategoryController().createCategory(category);
                    if (responseMessage.getMessage().equals("category_existed")) {
                        System.err.println("Category existed!");
                        System.out.println("Enter the category's name: ");
                        name = Config.validateString();
                        category.setName(name);
                    } else {
                        System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                new CategoryView();
                break;
            }
            System.out.println(Config.ID_NOT_EXIST);
        }
    }

    public void deleteCategory() {
        boolean flag = false;
        System.out.println("Category List");
        for (Category category : categoryList) {
            System.out.printf("ID: %d - Name: %s\n", category.getId(), category.getName());
        }
        System.out.println("Enter an ID that you want to delete: ");
        while (true) {
            int id = Config.validateInt();
            Category category = categoryController.findCategoryById(id);
            if (category != null) {
                while (true) {
                    System.out.println("Are you sure to delete category " + category.getName() + ", please type Y/N?");
                    String choice = Config.validateString();
                    if (choice.equalsIgnoreCase("y")) {
                        categoryController.deleteCategoryById(id);
                        System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                        flag = true;
                        break;
                    }
                    if (choice.equalsIgnoreCase("n")) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                new CategoryView();
                break;
            }
            System.out.println(Config.ID_NOT_EXIST);
        }
    }

    public void showCategoryDetails() {
        while (true) {
            System.out.println("Enter an ID that you want to see details: ");
            int id = Config.validateInt();
            if (categoryController.findCategoryById(id) != null) {
                System.out.println(categoryController.findCategoryById(id));
            } else {
                System.out.println(Config.ID_NOT_EXIST);
            }
            System.out.println(Config.CONTINUE_BACK_MENU);
            String backMenu = Config.scanner().nextLine();
            if (backMenu.equalsIgnoreCase("back")) {
                new CategoryView();
                break;
            }
        }
    }
}
