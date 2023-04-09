package service.channel;

import config.Config;
import model.Channel;
import model.User;

import java.util.List;

public class ChannelServiceIMPL implements IChannelService {
    List<Channel> channels = new Config<Channel>().readFromFile(Config.PATH_CHANNEL);

    @Override
    public List<Channel> findAll() {
        return channels;
    }

    @Override
    public void save(Channel channel) {
        if (findById(channel.getId()) == null) {
            channels.add(channel);
        } else {
            int index = channels.indexOf(findById(channel.getId()));
            channels.set(index, channel);
        }
        new Config<Channel>().writeToFile(Config.PATH_CHANNEL, channels);
    }

    @Override
    public Channel findById(int id) {
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getId() == id) {
                return channels.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        int index = channels.indexOf(findById(id));
        channels.remove(index);
        new Config<Channel>().writeToFile(Config.PATH_CHANNEL, channels);
    }

    @Override
    public Channel findByName(String name) {
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getName().equalsIgnoreCase(name)) {
                return channels.get(i);
            }
        }
        return null;
    }

    @Override
    public User findChannelFollower(User user, Channel channel) {
        for (User u : channel.getFollowerList()) {
            if (u.getId() == u.getId())
                return u;
        }
        return null;
    }
}
