package config;

import model.Video;

import java.util.Comparator;

public class VideoComparator implements Comparator<Video> {
    public static VideoComparator videoComparatorInstance;
    public static VideoComparator getVideoComparatorInstance(){
        if (videoComparatorInstance == null)
            videoComparatorInstance = new VideoComparator();
        return videoComparatorInstance;
    }
    @Override
    public int compare(Video o1, Video o2) {
        return o2.getViews() - o1.getViews();
    }
}
