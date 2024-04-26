import javax.swing.*;
import java.awt.*;

public class RacingApplication
{

    public static void main(String[] args) {

        // Create JButtons for different purposes with text
        JButton startButton = new JButton("Start");
        startButton.setSize(startButton.getPreferredSize());
        startButton.setLocation(110, 90);
        // Display a message dialog when the button is clicked
        startButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Ready to play the game?"));
        JButton customiseButton = new JButton("Customise");
       customiseButton.setSize(customiseButton.getPreferredSize());
        customiseButton.setLocation(205, 90);
        JButton statsButton = new JButton("Stats");
        statsButton.setSize(statsButton.getPreferredSize());
        statsButton.setLocation(150, 160);

        String [] colours = {"brown", "black", "white", "grey"};
        // Create a JList with the list of colours
        JList<String> colourList = new JList<>(colours);
        // Create a JScrollPane and add the JList to it
        JScrollPane scrollPane = new JScrollPane(colourList);

        JFrame frame = new JFrame("Racing Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,400);
        frame.setVisible(true);
        frame.setLayout(null); // Use absolute positioning
        //mainPanel.setBackground(Color.c);

       frame.add(startButton);
       frame.add(customiseButton);
        frame.add(statsButton);


    }

}