package redis.myapplication.main.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Video {

    private String title;
    private String language;

}
