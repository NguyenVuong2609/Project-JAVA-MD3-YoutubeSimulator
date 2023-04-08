package controller;

import model.ListVideo;
import service.playlist.IPlayListService;
import service.playlist.PlayListServiceIMPL;

import java.util.List;

public class PlayListController {
    IPlayListService playListService = new PlayListServiceIMPL();
    public List<ListVideo> getPlayLists(){
        return playListService.findAll();
    }
    public void createPlayList(ListVideo listVideo){
        playListService.save(listVideo);
    }
    public ListVideo findPlayListById(int id){
        return playListService.findById(id);
    }
    public void deletePlayList(int id){
        playListService.deleteById(id);
    }
    public ListVideo findPlayListByName(String name){
        return playListService.findByName(name);
    }
}
