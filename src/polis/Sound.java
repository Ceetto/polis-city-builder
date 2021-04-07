package polis;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;

public class Sound {

    AudioClip mainTheme;

    public Sound(){

    }

    public void mainTheme(){
        Media media = new Media(new File("resources/polis/bgm/bgm-1.mp3").toURI().toString());
        mainTheme = new AudioClip(media.getSource());
        mainTheme.setCycleCount(AudioClip.INDEFINITE);
        mainTheme.play();
    }

    public void build(){
        Media media = new Media(new File("resources/polis/sfx/build.mp3").toURI().toString());
        AudioClip player = new AudioClip(media.getSource());
        player.play();
    }

    public void delete(){
        Media media = new Media(new File("resources/polis/sfx/break.mp3").toURI().toString());
        AudioClip player = new AudioClip(media.getSource());
        player.play();
    }

    public void upgrade(){
        Media media = new Media(new File("resources/polis/sfx/upgrade.mp3").toURI().toString());
        AudioClip player = new AudioClip(media.getSource());
        player.play();
    }

    public void click(){
        Media media = new Media(new File("resources/polis/sfx/click.mp3").toURI().toString());
        AudioClip player = new AudioClip(media.getSource());
        player.play();
    }
}
