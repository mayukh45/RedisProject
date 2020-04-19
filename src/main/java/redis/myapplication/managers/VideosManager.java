package redis.myapplication.managers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import redis.clients.jedis.Jedis;
import redis.myapplication.models.Topic;
import redis.myapplication.models.Video;

import java.util.Set;
import java.util.stream.Collectors;

public class VideosManager {

    @Inject
    Jedis client;

    @Inject
    Gson gson;

    public void addVideo(Topic topic, Video video){
        client.zadd(topic.name(), 0, gson.toJson(video));
    }

    public void likeVideo(Topic topic, Video video){
        client.zincrby(topic.name(), 1, gson.toJson(video));
    }

    public Set<Video> getAllVideosOfaTopic(Topic topic){
        return client.zrevrange(topic.name(), 0, client.zcard(topic.name()))
                .stream()
                .map(jsonString -> gson.fromJson(jsonString, Video.class))
                .collect(Collectors.toSet());
    }

    public double getNumberOfLikes(Topic topic, Video video){
        return client.zscore(topic.name(), gson.toJson(video));
    }

    public void removeVideo(Topic topic, Video video){
        client.zrem(topic.name(), gson.toJson(video));
    }
}
