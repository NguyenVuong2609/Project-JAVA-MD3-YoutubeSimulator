package service.channel;

import model.Channel;
import service.IGenericService;

public interface IChannelService extends IGenericService<Channel> {
    Channel findByName(String name);
}
