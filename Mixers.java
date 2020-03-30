import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class Mixers {

    /*
     * Source: http://stackoverflow.com/questions/3705581/java-sound-api-capturing-microphone
     */
    public static void getMixers() {
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos) {
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getSourceLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                System.out.println(info.getName());
                System.out.println("    " + lineInfo);
                Line line = null;
                try {
                    line = m.getLine(lineInfo);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                System.out.println("    " + line);
            }
            lineInfos = m.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                System.out.println("    " + lineInfo);
                Line line = null;
                try {
                    line = m.getLine(lineInfo);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                System.out.println("    " + line);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Mixers.getMixers();
    }
}