package com.github.lmh01.mgt2mt.windows;

import com.github.lmh01.mgt2mt.MadGamesTycoon2ModTool;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WindowAvailableMods extends JFrame {

    //TODO Make this panel look good and add the correct buttons (Back, Exit, Settings and the two "Mod" Buttons)

    //TODO maybe make it possible to add a new feature fia a step by step wizard.
    private JPanel contentPane;
    static WindowAvailableMods frame = new WindowAvailableMods();

    public static void createFrame(){
        EventQueue.invokeLater(() ->{
            try {
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public WindowAvailableMods(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 400);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(10, 100, 89, 23);
        btnBack.addActionListener(ignored -> {
            MadGamesTycoon2ModTool.createFrame();
            frame.dispose();
        });
        contentPane.add(btnBack);

        JButton buttonOpenAddGenreToGamesWindow = new JButton("Add genre to NPC_Games");
        buttonOpenAddGenreToGamesWindow.setBounds(10, 50, 200, 23);
        buttonOpenAddGenreToGamesWindow.addActionListener((ignored) -> {
            WindowAddGenreToGames.createFrame();
            frame.dispose();
        });
        contentPane.add(buttonOpenAddGenreToGamesWindow);
    }
}