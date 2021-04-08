package polis;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {

    MediaPlayer mainTheme;

    private boolean mute = false;

    public Sound(){
        Media media = new Media(new File("resources/polis/bgm/bgm-1.mp3").toURI().toString());
        //Media media = new Media("/polis/bgm/bgm-1.mp3");
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
            Media media = new Media(new File("resources/polis/sfx/" + sfx + ".mp3").toURI().toString());
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
