package com.example.runner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class FormatAudio {
    File ogAudioFile;

    public FormatAudio(File originalAudioFile)
    {
            ogAudioFile = originalAudioFile;
    }

    public void getRAW()
    {
        AudioFileFormat inFileFormat;
            File inFile;
            File outFile;
        try {
            inFile = ogAudioFile;
            outFile = new File(ogAudioFile.getPath() + "newFile.wav");
        } catch (NullPointerException ex) {
            System.out.println("Error: one of the ConvertFileToAIFF" +" parameters is null!");
            return;
        }
        try {
            // query file type
            inFileFormat = AudioSystem.getAudioFileFormat(inFile);
            if (inFileFormat.getType() != AudioFileFormat.Type.WAVE)
            {
                // inFile is not WAVE, so let's try to convert it.
                AudioInputStream inFileAIS =
                        AudioSystem.getAudioInputStream(inFile);
                inFileAIS.reset(); // rewind
                if (AudioSystem.isFileTypeSupported(
                        AudioFileFormat.Type.WAVE, inFileAIS)) {
                    // inFileAIS can be converted to WAVE.
                    // so write the AudioInputStream to the
                    // output file.
                    AudioSystem.write(inFileAIS,
                            AudioFileFormat.Type.WAVE, outFile);
                    System.out.println("Successfully made WAVE file, "
                            + outFile.getPath() + ", from "
                            + inFileFormat.getType() + " file, " +
                            inFile.getPath() + ".");
                    inFileAIS.close();
                    return; // All done now
                } else
                    System.out.println("Warning: WAVE conversion of "
                            + inFile.getPath()
                            + " is not currently supported by AudioSystem.");
            } else
                System.out.println("Input file " + inFile.getPath() +
                        " is WAVE." + " Conversion is unnecessary.");
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error: " + inFile.getPath()
                    + " is not a supported audio file type!");
            return;
        } catch (IOException e) {
            System.out.println("Error: failure attempting to read "
                    + inFile.getPath() + "!");
            return;
        }


    }
}
