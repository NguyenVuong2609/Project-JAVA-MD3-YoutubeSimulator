package service.playlist;

import model.ListVideo;
import service.IGenericService;

public interface IPlayListService extends IGenericService<ListVideo> {
    ListVideo findByName(String name);
}
