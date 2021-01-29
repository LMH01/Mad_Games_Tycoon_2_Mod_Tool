package com.github.lmh01.mgt2mt;

import com.github.lmh01.mgt2mt.dataStream.AnalyzeExistingGenres;
import com.github.lmh01.mgt2mt.dataStream.EditGenreFile;
import com.github.lmh01.mgt2mt.util.NewGenreManager;
import com.github.lmh01.mgt2mt.util.Settings;
import com.github.lmh01.mgt2mt.windows.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MadGamesTycoon2ModTool {
    private static JFrame frame;
    private static ArrayList<String> importedGameNames = new ArrayList();
    private static final Logger logger = LoggerFactory.getLogger(MadGamesTycoon2ModTool.class);
    public static void main(String[] args) {
        //NewGenreManager.resetVariablesToDefault();
        //Settings.importSettings();
        //NewGenreManager.openStepWindow(9);
        //AnalyzeFreeGenreIDs.analyzeIDs();
        //EditGenreFile.addGenre();
        MadGamesTycoon2ModTool window = new MadGamesTycoon2ModTool();
    }

    public MadGamesTycoon2ModTool(){
        this.initialize();
        if(!settingsImported){
            if(Settings.importSettings()){
                logger.info("Settings have been imported.");
                if(!Settings.testFolderForMGT2Exe(Settings.mgt2FilePath)){
                    logger.info("The MGT2 file path is invalid.");
                    Settings.setMgt2FilePath(true);
                }
                settingsImported = true;
            }else{
                logger.info("Settings where not imported.");
                Settings.setMgt2FilePath(true);
            }
        }
    }
    private static boolean settingsImported = false;

    public static void createFrame(){
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

     private void initialize(){
        frame = new JFrame();
        frame.setBounds(100, 100, 335, 160);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout((LayoutManager)null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JLabel labelTitle = new JLabel("Mad Games Tycoon 2 Mod Tool");
        labelTitle.setBounds(60, 0, 260, 23);
        labelTitle.setForeground(Color.BLACK);
        labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        frame.getContentPane().add(labelTitle);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.setBounds(10, 100, 100, 23);
        buttonQuit.addActionListener(e -> {
            Settings.exportSettings();
            System.exit(0);
        });
        frame.getContentPane().add(buttonQuit);

        JButton buttonSettings = new JButton("Settings");
        buttonSettings.setBounds(230, 70, 90, 23);
        buttonSettings.addActionListener(e -> {
            WindowSettings.createFrame();
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
        buttonBackup.setBounds(10, 70, 100, 23);
        buttonBackup.addActionListener(e -> {
            WindowBackup.createFrame();
            frame.dispose();
        });
        frame.getContentPane().add(buttonBackup);

         JButton buttonOther = new JButton("Other");
         buttonOther.setBounds(120, 70, 100, 23);
         buttonOther.addActionListener(e -> {
             WindowOther.createFrame();
             frame.dispose();
         });
         frame.getContentPane().add(buttonOther);

        JButton buttonAvailableMods = new JButton("Begin");
        buttonAvailableMods.setBounds(10, 40, 310, 23);
        buttonAvailableMods.addActionListener(e -> {
            WindowAvailableMods.createFrame();
            frame.dispose();
        });
        frame.getContentPane().add(buttonAvailableMods);

        JLabel labelVersion = new JLabel("V1.0");
        labelVersion.setBounds(268, 100, 150, 23);
        frame.getContentPane().add(labelVersion);

        JLabel labelBy = new JLabel("by LMH01");
        labelBy.setBounds(135, 14, 70, 23);
        frame.getContentPane().add(labelBy);
    }
}

