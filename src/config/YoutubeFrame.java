package config;

import model.Video;

import java.util.concurrent.TimeUnit;

public class YoutubeFrame{
    public static YoutubeFrame youtubeFrameInstance;
    public static YoutubeFrame getYoutubeViewInstance(){
        if (youtubeFrameInstance == null)
            youtubeFrameInstance = new YoutubeFrame();
        return youtubeFrameInstance;
    }
    public void showVideoFrame(Video video) {
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.printf("|%45s%-53s"," ","YOUTUBE");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("+--------------------------------------------------------------------------------------------------+");
    }
    public void progressBar(Video video){
        int i = 0;
        int j;
        try {
            while (i <= 100) {
                showVideoFrame(video);
                j = 0;
                for (j = 0; j < i; j++) {
                    System.out.print('■');
                }
                for (; j <= 100; j++) System.out.print(' ');
                System.out.print(i + "%");
                TimeUnit.SECONDS.sleep(1);
                i += 10;
                System.out.println();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
