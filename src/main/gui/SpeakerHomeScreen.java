package main.gui;

import main.controllers.ProgramController;

import javax.swing.*;

public class SpeakerHomeScreen extends JFrame {
    private ProgramController programController;
    private JButton logOutButton;
    private JPanel panel1;
    private JButton listOfTalksButton;
    private JButton messagesButton;
    private JList TalksList;

    public SpeakerHomeScreen(ProgramController programController) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.programController = programController;
    }
}
