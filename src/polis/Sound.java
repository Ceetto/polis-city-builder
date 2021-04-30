package polis;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {

    final MediaPlayer mainTheme;

    private boolean mute = false;

    public Sound(){
        Media media = new Media(getClass().getResource("/polis/bgm/bgm-0.mp3").toExternalForm());
        mainTheme = new MediaPlayer(media);
        mainTheme.setCycleCount(AudioClip.INDEFINITE);
    }

    public void mainTheme(){
        mainTheme.play();
    }

    public void build(){
        sfx("build");
    }

    public void delete(){
        sfx("break");
    }

    public void upgrade(){
        sfx("upgrade");
    }

    public void click(){
        sfx("click");
    }

    public void sfx(String sfx){
        if(!mute) {
            Media media = new Media(getClass().getResource("/polis/sfx/" + sfx + ".mp3").toExternalForm());
            AudioClip player = new AudioClip(media.getSource());
            player.play();
        }
    }

    public void mute(){
        if(!mute) {
            mainTheme.setMute(true);
            mute = true;
        }else {
            mainTheme.setMute(false);
            mute = false;
        }
    }
}
