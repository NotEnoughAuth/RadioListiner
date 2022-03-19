package com.example.runner;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FormatAudio {
    File ogAudioFile;

    public FormatAudio(File originalAudioFile)
    {
            ogAudioFile = originalAudioFile;
    }

        public static void mp3ToWav(File mp3Data) throws UnsupportedAudioFileException, IOException {
            // open stream
            AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3Data);
            AudioFormat sourceFormat = mp3Stream.getFormat();
            // create audio format object for the desired stream/audio format
            // this is *not* the same as the file format (wav)
            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(), 16,
                    sourceFormat.getChannels(),
                    sourceFormat.getChannels() * 2,
                    sourceFormat.getSampleRate(),
                    false);
            // create stream that delivers the desired format
            AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
            // write stream into a file with file format wav
            AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File("C:\\tmp\\out.wav"));
        }
}
