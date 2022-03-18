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

    public void getRAW()
    {
        AudioFileFormat inFileFormat;
            File inFile;
            File outFile;
        try {
            inFile = ogAudioFile;
            outFile = new File(ogAudioFile.getPath() + "newFile.wav");
        } catch (NullPointerException ex) {
            System.out.println("Error: one of the ConvertFileToWAVE" +" parameters is null!");
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

    public byte [] getAudioDataBytes(AudioFormat audioFormat) throws UnsupportedAudioFileException, IllegalArgumentException, Exception{
        byte[] sourceBytes = Files.readAllBytes(Path.of(ogAudioFile.getPath()));
        if(sourceBytes == null || sourceBytes.length == 0 || audioFormat == null){
            throw new IllegalArgumentException("Illegal Argument passed to this method");
        }

        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        AudioInputStream sourceAIS = null;
        AudioInputStream convert1AIS = null;
        AudioInputStream convert2AIS = null;

        try{
            bais = new ByteArrayInputStream(sourceBytes);
            sourceAIS = AudioSystem.getAudioInputStream(bais);
            AudioFormat sourceFormat = sourceAIS.getFormat();
            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels()*2, sourceFormat.getSampleRate(), false);
            convert1AIS = AudioSystem.getAudioInputStream(convertFormat, sourceAIS);
            convert2AIS = AudioSystem.getAudioInputStream(audioFormat, convert1AIS);

            baos = new ByteArrayOutputStream();

            byte [] buffer = new byte[8192];
            while(true){
                int readCount = convert2AIS.read(buffer, 0, buffer.length);
                if(readCount == -1){
                    break;
                }
                baos.write(buffer, 0, readCount);
            }
            return baos.toByteArray();
        } catch(UnsupportedAudioFileException uafe){
            //uafe.printStackTrace();
            throw uafe;
        } catch(IOException ioe){
            //ioe.printStackTrace();
            throw ioe;
        } catch(IllegalArgumentException iae){
            //iae.printStackTrace();
            throw iae;
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        }finally{
            if(baos != null){
                try{
                    baos.close();
                }catch(Exception e){
                }
            }
            if(convert2AIS != null){
                try{
                    convert2AIS.close();
                }catch(Exception e){
                }
            }
            if(convert1AIS != null){
                try{
                    convert1AIS.close();
                }catch(Exception e){
                }
            }
            if(sourceAIS != null){
                try{
                    sourceAIS.close();
                }catch(Exception e){
                }
            }
            if(bais != null){
                try{
                    bais.close();
                }catch(Exception e){
                }
            }
        }
    }

}
