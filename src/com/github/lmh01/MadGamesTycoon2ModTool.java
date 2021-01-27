package com.github.lmh01;

import com.github.lmh01.windows.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MadGamesTycoon2ModTool {
    private static JFrame frame;
    private static ArrayList<String> importedGameNames = new ArrayList();
    public static void main(String[] args) {
        MadGamesTycoon2ModTool window = new MadGamesTycoon2ModTool();
    }
    public MadGamesTycoon2ModTool(){
        this.initialize();
    }

    public static void createFrame(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize(){
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 335, 160);
        this.frame.setDefaultCloseOperation(3);
        this.frame.getContentPane().setLayout((LayoutManager)null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        JLabel labelTitle = new JLabel("Mad Games Tycoon 2 Mod Tool");
        labelTitle.setBounds(60, 0, 260, 23);
        labelTitle.setForeground(Color.BLACK);
        labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        this.frame.getContentPane().add(labelTitle);

        /*JButton buttonAddGenre = new JButton("Add new Genre");
        buttonAddGenre.setBounds(30, 40, 150, 23);
        buttonAddGenre.setToolTipText("Click to add a new Genre to MGT2");
        buttonAddGenre.addActionListener(e -> {
            WindowAddNewGenre.createFrame();
        });
        this.frame.getContentPane().add(buttonAddGenre);*/

        /*JButton buttonAddGenreToGames = new JButton("NPC game list");
        buttonAddGenreToGames.setBounds(190, 40, 200, 23);
        buttonAddGenreToGames.setToolTipText("Click to add/remove a new Genre to/from the npc game list");
        buttonAddGenreToGames.addActionListener(e -> {
            WindowAddGenreToGames.createFrame();
        });
        this.frame.getContentPane().add(buttonAddGenreToGames);*/

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.setBounds(10, 100, 100, 23);
        buttonQuit.addActionListener(e -> {
            System.exit(0);
        });
        frame.getContentPane().add(buttonQuit);

        JButton buttonSettings = new JButton("Settings");
        buttonSettings.setBounds(230, 70, 90, 23);
        buttonSettings.addActionListener(e -> {
            WindowSettings.createFrame();
            frame.dispose();
        });
        frame.getContentPane().add(buttonSettings);

        JButton buttonChangelog = new JButton("Changelog");
        buttonChangelog.setBounds(120, 100, 100, 23);
        buttonChangelog.addActionListener(e -> {
            WindowChangelog.createFrame();
            frame.dispose();
        });
        frame.getContentPane().add(buttonChangelog);

        JButton buttonBackup = new JButton("Backup");
        buttonBackup.setBounds(10, 70, 210, 23);
        buttonBackup.addActionListener(e -> {
            WindowBackup.createFrame();
            frame.dispose();
        });
        frame.getContentPane().add(buttonBackup);

        JButton buttonAvailableMods = new JButton("Begin");
        buttonAvailableMods.setBounds(10, 40, 310, 23);
        buttonAvailableMods.addActionListener(e -> {
            WindowAvailableMods.createFrame();
            frame.dispose();
        });
        frame.getContentPane().add(buttonAvailableMods);

        JLabel labelVersion = new JLabel("V1.0");
        labelVersion.setBounds(268, 100, 150, 23);
        this.frame.getContentPane().add(labelVersion);

        JLabel labelBy = new JLabel("by LMH01");
        labelBy.setBounds(135, 14, 70, 23);
        this.frame.getContentPane().add(labelBy);
    }
    /*String inputFileLocation = "";

        JFileChooser fileChooser = new JFileChooser();
        int return_value = fileChooser.showOpenDialog(null);
        if(return_value == JFileChooser.APPROVE_OPTION) {
            inputFileLocation = fileChooser.getSelectedFile().getPath();
        }
        File inputFile = new File(inputFileLocation);
        try {
            Scanner scanner = new Scanner(inputFile);
            while(scanner.hasNextLine()){
                importedGameNames.add(scanner.nextLine());
            }
            JOptionPane.showMessageDialog(new Frame(), "Game names have been imported.\n Adding genres...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return_value = fileChooser.showSaveDialog(null);
        if(return_value == JFileChooser.APPROVE_OPTION){
            File outputFile = new File(fileChooser.getSelectedFile().getPath());
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
                for(int n = 0; n<importedGameNames.size(); n++){
                    pw.print(importedGameNames.get(n) + "\n");
                }
                pw.close();
                JOptionPane.showMessageDialog(new Frame(), "Operation successful");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
}

