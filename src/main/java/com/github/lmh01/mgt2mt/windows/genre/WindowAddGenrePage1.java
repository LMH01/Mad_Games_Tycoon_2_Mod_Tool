package com.github.lmh01.mgt2mt.windows.genre;

import com.github.lmh01.mgt2mt.dataStream.AnalyzeExistingGenres;
import com.github.lmh01.mgt2mt.util.NewGenreManager;
import com.github.lmh01.mgt2mt.windows.WindowAddNewGenre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WindowAddGenrePage1 extends JFrame{
    private JPanel contentPane;
    static WindowAddGenrePage1 frame = new WindowAddGenrePage1();
    private static Logger logger = LoggerFactory.getLogger(WindowAddGenrePage1.class);
    public static void createFrame(){
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public WindowAddGenrePage1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 335, 160);
        setResizable(false);
        setTitle("[Page 1] Text and id");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel labelGenreName = new JLabel("Genre name: ");
        labelGenreName.setBounds(10, 10, 100, 23);
        labelGenreName.setToolTipText("This name will be used for every translation.");
        contentPane.add(labelGenreName);

        JTextField textFieldGenreName = new JTextField();
        textFieldGenreName.setBounds(120, 10, 100, 23);
        textFieldGenreName.setText(NewGenreManager.name);
        contentPane.add(textFieldGenreName);

        JLabel labelGenreDescription = new JLabel("Genre description: ");
        labelGenreDescription.setBounds(10, 35, 120, 23);
        labelGenreDescription.setToolTipText("This description will be used for every translation.");
        contentPane.add(labelGenreDescription);

        JTextField textFieldGenreDescription = new JTextField();
        textFieldGenreDescription.setBounds(120, 35, 100, 23);
        textFieldGenreDescription.setText(NewGenreManager.description);
        textFieldGenreDescription.setToolTipText("Enter the genre description that should be shown in game. Hint: use <br> in your description to make a new line.");
        contentPane.add(textFieldGenreDescription);

        JLabel labelGenreID = new JLabel("Genre id: ");
        labelGenreID.setBounds(10, 60, 120, 23);
        contentPane.add(labelGenreID);

        JSpinner spinnerId = new JSpinner();
        spinnerId.setBounds(120, 60, 100, 23);
        spinnerId.setModel(new SpinnerNumberModel(NewGenreManager.id, NewGenreManager.id, 999, 1));
        spinnerId.setToolTipText("This is the unique id with which your genre can be found. It is recommended to leave this setting alone. Do only change it when you get an error.");
        contentPane.add(spinnerId);

        JButton buttonNext = new JButton("Next");
        buttonNext.setBounds(220, 100, 100, 23);
        buttonNext.setToolTipText("Click to continue to the next step.");
        buttonNext.addActionListener((ignored) -> {
            if(saveInputs(true, spinnerId, textFieldGenreName, textFieldGenreDescription)){
                NewGenreManager.openStepWindow(2);
                frame.dispose();
            }
        });
        contentPane.add(buttonNext);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.setBounds(120, 100, 90, 23);
        buttonQuit.addActionListener((ignored) -> {
            if(JOptionPane.showConfirmDialog((Component)null, "Are you sure?\nYour progress will be lost.", "Cancel add new genre", 0) == 0){
                WindowAddNewGenre.createFrame();
                frame.dispose();
            }
        });
        contentPane.add(buttonQuit);
    }
    private static boolean saveInputs(boolean calledFromNext, JSpinner spinnerId, JTextField textFieldGenreName, JTextField textFieldGenreDescription){
        if(AnalyzeExistingGenres.genreIDsInUse.contains(Integer.parseInt(spinnerId.getValue().toString()))){
            JOptionPane.showMessageDialog(new Frame(), "Please enter a different genre id.\nYour id is already in use!");
            return false;
        }else{
            NewGenreManager.id = Integer.parseInt(spinnerId.getValue().toString());
            logger.info("genre id: " + Integer.parseInt(spinnerId.getValue().toString()));
            try{
                if(textFieldGenreDescription.getText().isEmpty() || textFieldGenreName.getText().isEmpty()){
                    if(calledFromNext){
                        JOptionPane.showMessageDialog(new Frame(), "Please enter a name and description first.");
                        return false;
                    }else{
                        return true;
                    }

                }else{
                    NewGenreManager.name = textFieldGenreName.getText();
                    logger.info("genre name: " + textFieldGenreName.getText());
                    NewGenreManager.description = textFieldGenreDescription.getText();
                    logger.info("genre description: " + textFieldGenreDescription.getText());
                    return true;
                }
            }catch (NullPointerException e){
                logger.info("Something went wrong.");
                if(calledFromNext){
                    JOptionPane.showMessageDialog(new Frame(), "Please enter a name and description first.");
                }
                return false;
            }
        }
    }
}