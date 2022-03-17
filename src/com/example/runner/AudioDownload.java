package com.example.runner;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;

public class AudioDownload {
    URL url;
    File downloadedAudioFile;
    final int fileSize = 262144;

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
        while (audioFile.length() < fileSize) {
            if ((audioFile.length() % (fileSize/8)) == 0)
                System.out.print("*");
            len = is.read(buffer);
            outstream.write(buffer, 0, len);
        }
        outstream.close();

        downloadedAudioFile = audioFile;

        return 1;
    }

    public File getAudioFile()
    {
        return downloadedAudioFile;
    }
}
