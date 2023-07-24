import org.bytedeco.opencv.presets.opencv_core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class GUI extends JFrame implements ActionListener {

    private JTextField playerNameField;
    private JLabel infoLabel;
    private JTextArea infoArea;
    private boolean isStarted = false;

    public GUI() {
        // Set window properties
        setTitle("TFT Bot");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JPanel playerNamePanel = new JPanel();
        playerNameField = new JTextField(15);
        infoLabel = new JLabel("First set your Username and then press start.");
        JLabel playNamePreText = new JLabel("Playername: ");
        playerNamePanel.add(playNamePreText);
        playerNamePanel.add(playerNameField);
        JTextArea infoArea = new JTextArea(10, 20);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        JButton startStopButton = new JButton("Start / Stop");
        startStopButton.addActionListener(this);

        // Set layout to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add components to the frame using GridBagConstraints

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(playerNamePanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(infoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(scrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(startStopButton, constraints);

        // Show the window
        setVisible(true);

        // Show the window
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button click events here
        if (e.getActionCommand().equals("Start / Stop")) {
            if (!isStarted) {
                String playerName = playerNameField.getText().trim();
                if (!playerName.isEmpty()) {
                    infoLabel.setText("Spielername: " + playerName);
                    playerNameField.setEditable(false); // Deaktiviere die Bearbeitung des Eingabefeldes
                    try {
                        TFTBot.Run(getPlayerName());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    isStarted = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Bitte einen Spielername eingeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Code für Stopp-Button-Aktion hier
                infoLabel.setText("Das Spiel wurde gestoppt.");
                playerNameField.setEditable(true); // Aktiviere die Bearbeitung des Eingabefeldes
                isStarted = false;
            }
        }
    }

    public String getPlayerName(){
        return playerNameField.getText();
    }
}
