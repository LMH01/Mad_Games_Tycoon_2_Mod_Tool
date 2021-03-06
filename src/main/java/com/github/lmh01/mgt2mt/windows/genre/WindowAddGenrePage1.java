package com.github.lmh01.mgt2mt.windows.genre;

import com.github.lmh01.mgt2mt.data_stream.AnalyzeExistingGenres;
import com.github.lmh01.mgt2mt.data_stream.EditGenreFile;
import com.github.lmh01.mgt2mt.data_stream.ExportSettings;
import com.github.lmh01.mgt2mt.util.GenreManager;
import com.github.lmh01.mgt2mt.util.Settings;
import com.github.lmh01.mgt2mt.util.TranslationManager;
import com.github.lmh01.mgt2mt.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WindowAddGenrePage1 extends JFrame{
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowAddGenrePage1.class);
    static final WindowAddGenrePage1 FRAME = new WindowAddGenrePage1();
    private static Map<String, String> mapNameTranslations = new HashMap<>();
    private static Map<String, String> mapDescriptionTranslations = new HashMap<>();
    public static boolean nameTranslationsAdded = false;
    public static boolean descriptionTranslationsAdded = false;
    JPanel contentPane = new JPanel();
    JLabel labelGenreID = new JLabel("Genre id: ");
    JButton buttonExplainGenreID = new JButton("id?");
    JButton buttonNext = new JButton("Next");
    JButton buttonQuit = new JButton("Cancel");
    JButton buttonAddNameTranslations = new JButton("TRANSL");
    JButton buttonAddDescriptionTranslations = new JButton("TRANSL");
    JButton buttonClearTranslations = new JButton("Clear Translations");
    JTextField textFieldGenreName = new JTextField();
    JTextField textFieldGenreDescription = new JTextField();
    final JSpinner spinnerId = new JSpinner();

    public static void createFrame(){
        EventQueue.invokeLater(() -> {
            try {
                FRAME.setGuiComponents();
                FRAME.setVisible(true);
                FRAME.setLocationRelativeTo(null);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public WindowAddGenrePage1() {
        buttonNext.addActionListener(actionEvent -> {
            if(saveInputs(spinnerId, textFieldGenreName, textFieldGenreDescription)){
                GenreManager.openStepWindow(2);
                FRAME.dispose();
            }
        });

        buttonQuit.addActionListener(actionEvent -> {
            if(Utils.showConfirmDialog(1)) {
                FRAME.dispose();
            }
        });

        buttonAddNameTranslations.addActionListener(actionEvent ->{
            if(!nameTranslationsAdded){
                addNameTranslations();
                nameTranslationsAdded = true;
            }else{
                if(JOptionPane.showConfirmDialog(null, "Name translations have already been added.\nDo you want to clear the translations and add new ones?") == JOptionPane.OK_OPTION){
                    addNameTranslations();
                    nameTranslationsAdded = true;
                }
            }
        });

        buttonAddDescriptionTranslations.addActionListener(actionEvent ->{
            if(!descriptionTranslationsAdded){
                addDescriptionTranslations();
                descriptionTranslationsAdded = true;
            }else{
                if(JOptionPane.showConfirmDialog(null, "Description translations have already been added.\nDo you want to clear the translations and add new ones?") == JOptionPane.OK_OPTION){
                    addDescriptionTranslations();
                    descriptionTranslationsAdded = true;
                }
            }
        });
        buttonClearTranslations.addActionListener(actionEvent ->{
            if(JOptionPane.showConfirmDialog(null, "Are you sure that you want\nto reset the translations?", "Reset Translations?", JOptionPane.YES_NO_OPTION) == 0){
                GenreManager.mapNameTranslations.clear();
                GenreManager.mapDescriptionTranslations.clear();
                JOptionPane.showMessageDialog(null, "The translations have been cleared.");
            }
        });
    }

    private void setGuiComponents(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("[Page 1] Name and Description");

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        if(Settings.disableSafetyFeatures){
            setBounds(100, 100, 335, 190);
            spinnerId.setModel(new SpinnerNumberModel(0, 0, 999, 1));
            spinnerId.setToolTipText("<html>[Range: 0-999]<br>This is the unique id for your genre.<br>Do not change it unless you have your own genre id system.");
            spinnerId.setEnabled(true);
            spinnerId.setVisible(true);
            spinnerId.setBounds(120, 95, 100, 23);
            contentPane.add(spinnerId);
            labelGenreID.setVisible(true);
            buttonExplainGenreID.setVisible(true);
            buttonNext.setBounds(220, 130, 100, 23);
            buttonQuit.setBounds(120, 130, 90, 23);
            buttonExplainGenreID.setBounds(230, 95, 80, 23);
            buttonExplainGenreID.setToolTipText("Click to learn what the genre id is");
            buttonExplainGenreID.addActionListener(actionEvent -> JOptionPane.showMessageDialog(null, "The genre id is the unique id under which your genre can be found.\nWhenever a game file is modified that should reference your genre this genre id is used.", "Genre id", JOptionPane.INFORMATION_MESSAGE));
            contentPane.add(buttonExplainGenreID);
        }else{
            setBounds(100, 100, 335, 160);
            spinnerId.setModel(new SpinnerNumberModel(AnalyzeExistingGenres.getFreeGenreID(), AnalyzeExistingGenres.getFreeGenreID(), AnalyzeExistingGenres.getFreeGenreID(), 1));
            spinnerId.setToolTipText("<html>[Range: Automatic]<br>This is the unique id for your genre.<br>It can only be changed when the safety features are disabled fia the settings.");
            spinnerId.setEnabled(false);
            spinnerId.setVisible(false);
            labelGenreID.setVisible(false);
            buttonExplainGenreID.setVisible(false);
            buttonNext.setBounds(220, 100, 100, 23);
            buttonQuit.setBounds(120, 100, 90, 23);
        }


        JLabel labelGenreName = new JLabel("Genre name: ");
        labelGenreName.setBounds(10, 10, 100, 23);
        labelGenreName.setToolTipText("This name will be used for every translation.");
        contentPane.add(labelGenreName);


        textFieldGenreName.setBounds(120, 10, 100, 23);
        textFieldGenreName.setToolTipText("<html>This is the global genre name.<br>This name is being displayed in every translation.");
        textFieldGenreName.setText(GenreManager.mapNewGenre.get("NAME EN"));
        contentPane.add(textFieldGenreName);

        buttonAddNameTranslations.setBounds(230, 10, 90, 23);
        buttonAddNameTranslations.setToolTipText("Click to add name translations for your genre.");
        contentPane.add(buttonAddNameTranslations);

        JLabel labelGenreDescription = new JLabel("Genre description: ");
        labelGenreDescription.setBounds(10, 35, 120, 23);
        labelGenreDescription.setToolTipText("This description will be used for every translation.");
        contentPane.add(labelGenreDescription);

        labelGenreID.setBounds(10, 95, 120, 23);
        contentPane.add(labelGenreID);

        textFieldGenreDescription.setBounds(120, 35, 100, 23);
        textFieldGenreDescription.setText(GenreManager.mapNewGenre.get("DESC EN"));
        textFieldGenreDescription.setToolTipText("Enter the genre description that should be shown in game. Hint: use <br> in your description to make a new line.");
        contentPane.add(textFieldGenreDescription);

        buttonAddDescriptionTranslations.setBounds(230,35,90, 23);
        buttonAddDescriptionTranslations.setToolTipText("Click to add description translations for your genre.");
        contentPane.add(buttonAddDescriptionTranslations);

        buttonClearTranslations.setBounds(120,65,200,23);
        buttonClearTranslations.setToolTipText("Click to remove all Translations");
        contentPane.add(buttonClearTranslations);

        buttonNext.setToolTipText("Click to continue to the next step.");
        contentPane.add(buttonNext);

        buttonQuit.setToolTipText("Click to quit this step by step guide and return to the add genre page.");
        contentPane.add(buttonQuit);
    }
    private static boolean saveInputs(JSpinner spinnerId, JTextField textFieldGenreName, JTextField textFieldGenreDescription){
        if(AnalyzeExistingGenres.getGenreIdsInUse().contains(spinnerId.getValue())){
            JOptionPane.showMessageDialog(new Frame(), "Please enter a different genre id.\nYour id is already in use!");
            return false;
        }else if(AnalyzeExistingGenres.getGenreNamesInUse().contains(textFieldGenreName.getToolTipText())){
            JOptionPane.showMessageDialog(new Frame(), "Please enter a different genre name.\nYour name is already in use!");
            return false;
        }else if(textFieldGenreName.getText().isEmpty()){
            JOptionPane.showMessageDialog(new Frame(), "Please enter a genre name first!");
            return false;
        }else if(textFieldGenreDescription.getText().isEmpty()){
            JOptionPane.showMessageDialog(new Frame(), "Please enter a genre description first!");
        }else{
            GenreManager.mapNewGenre.remove("NAME EN");
            GenreManager.mapNewGenre.remove("DESC EN");
            if(Settings.disableSafetyFeatures){
                GenreManager.mapNewGenre.remove("ID");
                GenreManager.mapNewGenre.put("ID", spinnerId.getValue().toString());
            }
            GenreManager.mapNewGenre.put("NAME EN", textFieldGenreName.getText());
            GenreManager.mapNewGenre.put("DESC EN", textFieldGenreDescription.getText());
            return true;
        }
        return false;
    }


    /**
     * Call to add nameTranslations to the genre.
     */
    public static void addNameTranslations(){
        boolean continueWithTranslations = true;
        if(Settings.enableGenreNameTranslationInfo){
            JCheckBox checkBoxDontShowAgain = new JCheckBox("Don't show this information again");
            JLabel labelMessage = new JLabel("<html>Note:<br>The translation that you have entered in the \"main\"text field<br>will be used as the english translation.<br><br>Continue?");
            Object[] params = {labelMessage,checkBoxDontShowAgain};
            LOGGER.info("enableGenreNameTranslationInfo: " + Settings.enableGenreDescriptionTranslationInfo);
            if(JOptionPane.showConfirmDialog(null, params, "Information", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != 0){
                continueWithTranslations = false;
            }
            Settings.enableGenreNameTranslationInfo = !checkBoxDontShowAgain.isSelected();
            ExportSettings.export();
        }
        if(continueWithTranslations){
            mapNameTranslations = TranslationManager.getTranslationsMap();
            nameTranslationsAdded = true;
        }
    }

    /**
     * Call to add descriptionTranslations to the genre.
     */
    public static void addDescriptionTranslations(){
        boolean continueWithTranslations = true;
        if(Settings.enableGenreDescriptionTranslationInfo){
            JCheckBox checkBoxDontShowAgain = new JCheckBox("Don't show this information again");
            JLabel labelMessage = new JLabel("<html>Note:<br>The translation that you have entered in the \"main\"text field<br>will be used as the english translation.<br><br>Continue?");
            Object[] params = {labelMessage,checkBoxDontShowAgain};
            LOGGER.info("enableGenreDescriptionTranslationInfo: " + Settings.enableGenreDescriptionTranslationInfo);
            if(JOptionPane.showConfirmDialog(null, params, "Information", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != 0){
                continueWithTranslations = false;
            }
            Settings.enableGenreDescriptionTranslationInfo = !checkBoxDontShowAgain.isSelected();
            ExportSettings.export();
        }
        if(continueWithTranslations){
            mapDescriptionTranslations = TranslationManager.getTranslationsMap();
        }
    }

    /**
     * Uses the maps {@link WindowAddGenrePage1#mapNameTranslations} and {@link WindowAddGenrePage1#mapDescriptionTranslations} to parse new map that contains evey translation. When the maps are empty all translation will be set to english
     * @return Returns a map containg all genre translations in the format that they are read in {@link EditGenreFile}
     */
    public static Map<String, String> getMapGenreTranslations(){
        Map<String, String> mapGenreTranslation = new HashMap<>();
        if(mapNameTranslations.isEmpty()){
            for(String string : TranslationManager.TRANSLATION_KEYS){
                mapGenreTranslation.put("NAME " + string, GenreManager.mapNewGenre.get("NAME EN"));
            }
        }else{
            for(Map.Entry<String, String> entry : mapNameTranslations.entrySet()){
                mapGenreTranslation.put("NAME " + entry.getKey(), entry.getValue().toString());
            }
        }
        if(mapDescriptionTranslations.isEmpty()){
            for(String string : TranslationManager.TRANSLATION_KEYS){
                mapGenreTranslation.put("DESC " + string, GenreManager.mapNewGenre.get("DESC EN"));
            }
        }else{
            for(Map.Entry<String, String> entry : mapDescriptionTranslations.entrySet()){
                mapGenreTranslation.put("DESC " + entry.getKey(), entry.getValue().toString());
            }
        }
        return mapGenreTranslation;
    }

    /**
     * Clears the translation maps
     */
    public static void clearTranslationArrayLists(){
        mapNameTranslations.clear();
        mapDescriptionTranslations.clear();
    }
}
