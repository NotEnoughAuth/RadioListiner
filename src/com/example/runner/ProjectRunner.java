package com.example.runner;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProjectRunner {
    public static void main(String[] args) throws Exception {

        AudioDownload audio = new AudioDownload(new URL("https://retail-music.com/walmart_radio.mp3"));
        FormatAudio formattedAudio;

        audio.downladAudio();
        formattedAudio = new FormatAudio(audio.getAudioFile());

        formattedAudio.mp3ToWav(audio.getAudioFile());


    }
}
