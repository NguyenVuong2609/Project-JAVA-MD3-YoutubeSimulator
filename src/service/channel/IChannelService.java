package service.channel;

import model.Channel;
import model.User;
import service.IGenericService;

public interface IChannelService extends IGenericService<Channel> {
    Channel findByName(String name);
    User findChannelFollower(User user, Channel channel);
}
