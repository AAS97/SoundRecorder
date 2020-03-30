 /**
  * This is an input panel specifically designed to create a selection method for each microphone.
  * In this case, a microphone is defined as mixer and not a specific target data line for sake of
  * end user simplicity. This class extends JPanel and fires property change listeners when the
  * user selects a new mixer.
  * @author Aaron Gokaslan
  * This class was inspired by TarsosDSP.
  */

  import java.awt.BorderLayout;
  import java.awt.Dimension;
  import java.awt.GridLayout;
  import java.awt.event.ActionEvent;
  import java.awt.event.ActionListener;
  
  import javax.sound.sampled.AudioSystem;
  import javax.sound.sampled.Line;
  import javax.sound.sampled.Mixer;
  import javax.sound.sampled.TargetDataLine;
  import javax.swing.ButtonGroup;
  import javax.swing.JFrame;
  import javax.swing.JPanel;
  import javax.swing.JRadioButton;
  import javax.swing.JScrollPane;
  import javax.swing.border.TitledBorder;
  
  public class MicrophonePanel extends JPanel {
  
      /**
       * Auto-generated Serial Long
       */
      private static final long serialVersionUID = 1L;
      
      Mixer mixer = null;
      
      public MicrophonePanel(){
          super(new BorderLayout());
          this.setBorder(new TitledBorder("1. Choose a microphone input"));
          JPanel buttonPanel = new JPanel(new GridLayout(0,1));
          ButtonGroup group = new ButtonGroup();
          Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
          for (Mixer.Info info: mixerInfos){
              Mixer m = AudioSystem.getMixer(info);
              Line.Info[] lineInfos = m.getTargetLineInfo();
              if(lineInfos.length > 0 && lineInfos[0].getLineClass().equals(TargetDataLine.class)){
                  JRadioButton button = new JRadioButton();
                  button.setText(info.getName());
                  button.setActionCommand(info.toString());
                  button.addActionListener(setInput);
                  buttonPanel.add(button);
                  group.add(button);
              }
          }
          this.add(new JScrollPane(buttonPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
          this.setMaximumSize(new Dimension(300,150));
          this.setPreferredSize(new Dimension(300,150));
      }
      
      private ActionListener setInput = new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent arg0) {
              for(Mixer.Info info : AudioSystem.getMixerInfo()){
                  if(arg0.getActionCommand().equals(info.toString())){
                      Mixer newValue = AudioSystem.getMixer(info);
                      MicrophonePanel.this.firePropertyChange("mixer", mixer, newValue);
                      MicrophonePanel.this.mixer = newValue;
                      break;
                  }
              }
          }
      };
      
      //Example Method
      public static void main(String[] args){
          JFrame frame = new JFrame("Microphone Selection Test");
          MicrophonePanel panel = new MicrophonePanel();
          frame.getContentPane().add(panel);
          frame.setVisible(true);;
          frame.setLocationRelativeTo(null);
          frame.pack();
      }
  
  }