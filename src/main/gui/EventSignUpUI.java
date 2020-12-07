package main.gui;

import main.controllers.ProgramController;

import javax.swing.*;

public class EventSignUpUI extends JFrame {
    private JButton backButton;
    private JPanel panel1;
    private JButton signUpForAnButton;
    private JButton cancelAnEventButton;
    private JButton checkEventDetailsButton;
    private JList list1;
    private JButton confirmButton;
    private ProgramController programController;

    public EventSignUpUI(ProgramController programController) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.programController = programController;
    }
}