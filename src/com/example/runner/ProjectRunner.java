package com.example.runner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProjectRunner {
    public static void main(String[] args) throws IOException {
        System.out.println("HelloWorld");
        AudioDownload audio = new AudioDownload(new URL("https://retail-music.com/walmart_radio.mp3"));
        audio.getAudio();
    }
}
