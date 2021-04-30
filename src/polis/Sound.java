package polis;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * Speelt muziek en sound effects af
 */
public class Sound {

    private final MediaPlayer mainTheme;
    private boolean mute = false;

    public Sound(){
        Media media = new Media(Objects.requireNonNull(getClass().getResource("/polis/bgm/bgm-0.mp3"))
                .toExternalForm());
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
            Media media = new Media(Objects.requireNonNull(getClass().getResource("/polis/sfx/" + sfx + ".mp3"))
                    .toExternalForm());
            AudioClip player = new AudioClip(media.getSource());
            player.play();
        }
    }

    public void mute(){
        mute = !mute;
        mainTheme.setMute(mute);
    }
}
