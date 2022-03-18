package com.example.runner;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;

public class AudioDownload {
    URL url;
    File downloadedAudioFile;
    final int fileSize = 262144;
    final int progressbarLength = 10;

    public AudioDownload(URL u) {
        url = u;
    }

    public int downladAudio() throws IOException {

        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        File audioFile = new File("/tmp/AudioFile.mp3");
        OutputStream outstream = new FileOutputStream(audioFile);

        byte[] buffer = new byte[4096];
        int len = is.read(buffer);
        int progress = 1;

        System.out.print("Downloading File ");

        while (audioFile.length() < fileSize) {
            //create a visual progress bar to help impatience and worries
            if (audioFile.length() > (progress * fileSize / (progressbarLength + 1))) {
                System.out.print("#");
                progress++;
            }

            len = is.read(buffer);
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        System.out.print("\n");
        downloadedAudioFile = audioFile;



        return 1;
    }

    public File getAudioFile()
    {
        return downloadedAudioFile;
    }
}
