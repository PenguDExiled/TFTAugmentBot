import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame implements ActionListener {

    private JTextField playerNameField;
    private JLabel infoLabel;
    private JTextArea infoArea;
    private boolean isStarted = false;

    public GUI() {
        // Set window properties
        setTitle("TFT Bot");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JPanel playerNamePanel = new JPanel();
        playerNameField = new JTextField(15);
        infoLabel = new JLabel("First set your Username and then press start.");
        JLabel playNamePreText = new JLabel("Playername: ");
        playerNamePanel.add(playNamePreText);
        playerNamePanel.add(playerNameField);
        //JTextArea infoArea = new JTextArea(10, 20);
        infoArea = new JTextArea(20,30);
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
                    infoLabel.setText("programm running...");
                    playerNameField.setEditable(false); // Deaktiviere die Bearbeitung des Eingabefeldes
                    try {
                        TFTBot.Run(getPlayerName(), this);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    isStarted = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a username", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Zeige ein Bestätigungsfenster, wenn der Benutzer auf "Stop" klickt.
                int result = JOptionPane.showConfirmDialog(this,
                        "Would you really like to stop the program?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    // Code für Stopp-Button-Aktion hier
                    infoLabel.setText("program has been stopped.");
                    playerNameField.setEditable(true); // Aktiviere die Bearbeitung des Eingabefeldes
                    isStarted = false;
                }
            }
        }
    }

    public String getPlayerName(){
        return playerNameField.getText();
    }
    public  void setInfoArea(String input){
        infoArea.setText(input);
    }
    public  void addToInfoArea(String input){
        infoArea.setText(infoArea.getText() + "\n" + input);
    }
    public boolean checkIsRunning(){
        System.out.println("checking");
        return isStarted;
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }*/
}
