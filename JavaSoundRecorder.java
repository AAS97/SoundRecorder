import javax.sound.sampled.*;
import java.io.*;

public class JavaSoundRecorder {
    static final long RECORD_TIME = 3000;

    File wavFile = new File("/Users/aubrydandoque/Hinfact/SoundRecorder/file.wav");
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    TargetDataLine line;

    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian);

        return format;
    }

    void start() {
        try {
            AudioFormat format = getAudioFormat();
            
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            System.out.println("System info :/r/n" + info);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line Not Supported");
                System.exit(0);
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Start capturing...");
            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start Capturing...");

            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            ;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

    public static void main(String[] args) {
        final JavaSoundRecorder recorder = new JavaSoundRecorder();

        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                recorder.finish();
            }
        });

        stopper.start();

        recorder.start();
    }
}