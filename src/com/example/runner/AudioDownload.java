package com.example.runner;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;

public class AudioDownload {
    URL url;

    public AudioDownload(URL u) {
        url = u;
    }

    public int getAudio() throws IOException {

        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        File audioFile = new File("/tmp/AudioFile.mp3");
        OutputStream outstream = new FileOutputStream(audioFile);

        byte[] buffer = new byte[4096];
        int len = is.read(buffer);
        while (audioFile.length() < 262144) {
            len = is.read(buffer);
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        return 1;
    }
}
