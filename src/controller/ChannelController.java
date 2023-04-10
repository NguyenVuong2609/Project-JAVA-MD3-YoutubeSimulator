package controller;

import model.Channel;
import model.User;
import service.channel.ChannelServiceIMPL;
import service.channel.IChannelService;

import java.util.List;

public class ChannelController {
    public static ChannelController channelControllerInstance;

    public static ChannelController getChannelControllerInstance() {
        if (channelControllerInstance == null)
            channelControllerInstance = new ChannelController();
        return channelControllerInstance;
    }

    IChannelService channelService = new ChannelServiceIMPL();

    public List<Channel> getChannelList() {
        return channelService.findAll();
    }

    public void createChannel(Channel channel) {
        channelService.save(channel);
    }

    public Channel findChannelById(int id) {
        return channelService.findById(id);
    }

    public void deleteChannel(int id) {
        channelService.deleteById(id);
    }

    public void updateChannel(Channel channel) {
        channelService.save(channel);
    }

    public Channel findByName(String name) {
        return channelService.findByName(name);
    }

    public User findChannelFollower(User user, Channel channel) {
        return channelService.findChannelFollower(user, channel);
    }

    public Integer calTotalAllVideosView(Channel channel) {
        return channelService.calTotalAllVideosView(channel);
    }
}
