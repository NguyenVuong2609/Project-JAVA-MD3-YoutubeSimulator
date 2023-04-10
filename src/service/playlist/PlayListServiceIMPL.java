package service.playlist;

import config.Config;
import model.ListVideo;

import java.util.ArrayList;
import java.util.List;

public class PlayListServiceIMPL implements IPlayListService {
    List<ListVideo> listVideos = new Config<ListVideo>().readFromFile(Config.PATH_PLAYLIST);

    @Override
    public List<ListVideo> findAll() {
        return listVideos;
    }

    @Override
    public void save(ListVideo listVideo) {
        if (findById(listVideo.getId()) == null) {
            listVideos.add(listVideo);
        } else {
            int index = listVideos.indexOf(findById(listVideo.getId()));
            listVideos.set(index, listVideo);
        }
        new Config<ListVideo>().writeToFile(Config.PATH_PLAYLIST, listVideos);
    }

    @Override
    public ListVideo findById(int id) {
        for (int i = 0; i < listVideos.size(); i++) {
            if (listVideos.get(i).getId() == id) {
                return listVideos.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        int index = listVideos.indexOf(findById(id));
        listVideos.remove(index);
        new Config<ListVideo>().writeToFile(Config.PATH_PLAYLIST, listVideos);
    }

    @Override
    public ListVideo findByName(String name) {
        for (ListVideo listVideo : listVideos) {
            if (listVideo.getName().equals(name)) {
                return listVideo;
            }
        }
        return null;
    }

    @Override
    public List<ListVideo> findListOfPlayListByName(String name) {
        List<ListVideo> searchListVideo = new ArrayList<>();
        for (ListVideo list : listVideos) {
            if (list.getName().toLowerCase().contains(name.toLowerCase()))
                searchListVideo.add(list);
        }
        return searchListVideo;
    }
}
