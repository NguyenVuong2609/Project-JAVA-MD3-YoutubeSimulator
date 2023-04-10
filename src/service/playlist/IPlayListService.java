package service.playlist;

import model.ListVideo;
import service.IGenericService;

import java.util.List;

public interface IPlayListService extends IGenericService<ListVideo> {
    ListVideo findByName(String name);
    List<ListVideo> findListOfPlayListByName(String name);
}
