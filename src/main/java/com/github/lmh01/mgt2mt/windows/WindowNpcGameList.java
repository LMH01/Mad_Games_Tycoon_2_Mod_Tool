package com.github.lmh01.mgt2mt.windows;

import com.github.lmh01.mgt2mt.data_stream.AnalyzeExistingGenres;
import com.github.lmh01.mgt2mt.data_stream.NPCGameListChanger;
import com.github.lmh01.mgt2mt.util.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class WindowNpcGameList extends JFrame {

    static final WindowNpcGameList FRAME = new WindowNpcGameList();
    private final JPanel contentPane;
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowNpcGameList.class);
    final JSpinner SPINNER_GENRE_ID = new JSpinner();

    public static void createFrame(){
        EventQueue.invokeLater(() -> {
            try {
                FRAME.setSpinner();
                FRAME.setVisible(true);
                FRAME.setLocationRelativeTo(null);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public WindowNpcGameList(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 200, 200);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelAddNewGenre = new JLabel("NPC game list");
        labelAddNewGenre.setBounds(60, 10, 250, 23);
        contentPane.add(labelAddNewGenre);

        JLabel labelGenreID = new JLabel("Genre ID: ");
        labelGenreID.setBounds(10, 40, 70, 23);
        contentPane.add(labelGenreID);

        JLabel labelOperation = new JLabel("Operation: ");
        labelOperation.setToolTipText("<html>Add = Adds said genre id to the list<brRemove = Removes the genre id from the list");
        labelOperation.setBounds(10, 75, 70, 23);
        contentPane.add(labelOperation);

        JComboBox<String> comboBoxOperation = new JComboBox<>();
        comboBoxOperation.setBounds(100,75,80,23);
        comboBoxOperation.setToolTipText("<html>Add = Adds said genre id to the list<br>Remove = Removes the genre id from the list");
        comboBoxOperation.setModel(new DefaultComboBoxModel<>(new String[]{"Add", "Remove"}));
        contentPane.add(comboBoxOperation);

        JLabel labelChance = new JLabel("Chance: ");
        labelChance.setBounds(10,110,50,23);
        contentPane.add(labelChance);

        JSpinner spinnerChance = new JSpinner();
        spinnerChance.setBounds(100, 110, 80, 23);
        spinnerChance.setModel(new SpinnerNumberModel(20, 1, 100, 1));
        spinnerChance.setToolTipText("<html>Determines the chance at which the genre id should be added<br>100 = 100% chance.");
        contentPane.add(spinnerChance);

        JButton buttonApply = new JButton("Apply");
        buttonApply.setBounds(100,140,80,23);
        buttonApply.setToolTipText("Click to edit the NpcGames.txt file with your settings.");
        buttonApply.addActionListener(actionEvent -> {
            boolean addGenreID;
            String messageOperation;
            if(Objects.equals(comboBoxOperation.getSelectedItem(), "Add")) {
                addGenreID = true;
                messageOperation = "added to";
                LOGGER.info("operation: add genre id");
            }else{
                addGenreID = false;
                messageOperation = "removed from";
                LOGGER.info("operation: remove genre id");
            }
            int genreID = Integer.parseInt(SPINNER_GENRE_ID.getValue().toString());

            try {
                NPCGameListChanger.editNPCGames(genreID, addGenreID, Integer.parseInt(spinnerChance.getValue().toString()));
                JOptionPane.showMessageDialog(new Frame(), "Genre ID [" + genreID + "] has successfully\nbeen " + messageOperation + " the NpcGames list.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(new Frame(), "Error while adding genre with id [" + genreID + "] to NpcGames.txt.\nnPlease try again with administrator rights.\nException: " + e.getMessage(), "Unable to edit NpcGames.txt", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
        contentPane.add(buttonApply);

        JButton btnBack = new JButton("Cancel");
        btnBack.setBounds(10, 140, 80, 23);
        btnBack.setToolTipText("Click to get to the main page.");
        btnBack.addActionListener(actionEvent -> FRAME.dispose());
        contentPane.add(btnBack);
    }
    private void setSpinner(){
        SPINNER_GENRE_ID.setBounds(100,40,80,23);
        if(Settings.disableSafetyFeatures){
            SPINNER_GENRE_ID.setModel(new SpinnerNumberModel(0, 0, 999, 1));
        }else{
            SPINNER_GENRE_ID.setModel(new SpinnerNumberModel(AnalyzeExistingGenres.getFreeGenreID()-1, 0, AnalyzeExistingGenres.getFreeGenreID()-1, 1));
        }
        SPINNER_GENRE_ID.setToolTipText("Enter the ID to add to the games");
        contentPane.add(SPINNER_GENRE_ID);
    }
}
