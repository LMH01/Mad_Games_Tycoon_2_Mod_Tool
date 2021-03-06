package com.github.lmh01.mgt2mt.windows.genre;

import com.github.lmh01.mgt2mt.util.GenreManager;
import com.github.lmh01.mgt2mt.util.Utils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WindowAddGenrePage10 extends JFrame{
    static final WindowAddGenrePage10 FRAME = new WindowAddGenrePage10();
    public static ArrayList<File> arrayListScreenshotFiles = new ArrayList<>();
    JPanel contentPane = new JPanel();
    JButton buttonAddScreenshot = new JButton("Add screenshot(s)");
    JButton buttonResetAddedScreenshots = new JButton("Reset");
    JButton buttonNext = new JButton("Next");
    JButton buttonPrevious = new JButton("Previous");
    JButton buttonQuit = new JButton("Cancel");

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

    public WindowAddGenrePage10() {
        buttonAddScreenshot.addActionListener(actionEvent -> {
            ArrayList<File> arrayListScreenshotFilesSelected = new ArrayList<>();
            JTextField textFieldScreenshotFile = new JTextField();
            JLabel labelMessage = new JLabel("<html>Click browse or enter enter the image path manually." +
                    "<br>When the image path is set click okay." +
                    "<br>This will add the screenshot to a list of screenshots that will be shown in the development progress page." +
                    "<br>Note: The image file as to be a `.png` file and the aspect ratio should be 4:3");
            JButton buttonBrowse = new JButton("Browse");
            AtomicBoolean multipleFilesSelected = new AtomicBoolean(false);
            AtomicInteger numberOfScreenshotsToAdd = new AtomicInteger();
            buttonBrowse.addActionListener(actionEventSmall ->{
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set Look and Feel to Windows

                    FileFilter fileFilter = new FileFilter() {//File filter to only show .png files.
                        @Override
                        public boolean accept(File f) {
                            if(f.getName().contains(".png")){
                                return true;
                            }
                            return f.isDirectory();
                        }

                        @Override
                        public String getDescription() {
                            return ".png files";
                        }
                    };

                    JFileChooser fileChooser = new JFileChooser(); //Create a new GUI that will use the current(windows) Look and Feel
                    fileChooser.setFileFilter(fileFilter);
                    fileChooser.setDialogTitle("Choose a genre image (.png):");
                    fileChooser.setMultiSelectionEnabled(true);

                    int return_value = fileChooser.showOpenDialog(null);
                    if (return_value == 0) {
                        final int NUMBER_OF_SCREENSHOTS = fileChooser.getSelectedFiles().length;
                        numberOfScreenshotsToAdd.set(NUMBER_OF_SCREENSHOTS);
                        File[] screenshots = fileChooser.getSelectedFiles();
                        if(NUMBER_OF_SCREENSHOTS > 1){
                            multipleFilesSelected.set(true);
                        }
                        boolean failed = false;
                        for(int i=0; i<NUMBER_OF_SCREENSHOTS; i++){
                            if(!failed){
                                if(screenshots[i].getName().contains(".png")){
                                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //revert the Look and Feel back to the ugly Swing
                                    if(multipleFilesSelected.get()){
                                        arrayListScreenshotFilesSelected.add(screenshots[i]);
                                        textFieldScreenshotFile.setText("Multiple files selected");
                                    }else{
                                        textFieldScreenshotFile.setText(fileChooser.getSelectedFile().getPath());
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(new Frame(), "Please select only .png files.");
                                    failed = true;
                                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //revert the Look and Feel back to the ugly Swing
                                }
                            }
                        }
                    }
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //revert the Look and Feel back to the ugly Swing
                } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            });
            Object[] params = {labelMessage,textFieldScreenshotFile, buttonBrowse};
            if(JOptionPane.showConfirmDialog(null, params, "Add a screenshot", JOptionPane.OK_CANCEL_OPTION) == 0){
                String textFieldPath = textFieldScreenshotFile.getText();
                if(textFieldPath.endsWith(".png")){
                    File imageFile = new File(textFieldPath);
                    if(imageFile.exists()){
                        arrayListScreenshotFiles.add(new File(textFieldPath));
                        JOptionPane.showMessageDialog(new Frame(), "Image file has been added.");
                    }else{
                        JOptionPane.showMessageDialog(new Frame(), "The entered image file does not exist.\nPlease select a valid file.", "File not found", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(multipleFilesSelected.get()){
                    for(int i = 0; i< numberOfScreenshotsToAdd.get(); i++){
                        arrayListScreenshotFiles.add(arrayListScreenshotFilesSelected.get(i));
                    }
                    JOptionPane.showMessageDialog(new Frame(), "Image files have been added.");
                }else{
                    JOptionPane.showMessageDialog(new Frame(), "Please select a .png file.");

                }
            }
        });
        buttonResetAddedScreenshots.addActionListener(actionEvent -> {
            if(JOptionPane.showConfirmDialog(null, "<html>Are you sure that you want to reset<br> the added screenshots?", "Reset?", JOptionPane.YES_NO_OPTION) == 0){
                arrayListScreenshotFiles.clear();
            }
        });
        buttonNext.addActionListener(actionEvent -> {
            if(!arrayListScreenshotFiles.isEmpty()){
                StringBuilder filePaths = new StringBuilder();
                for (File arrayListScreenshotFile : arrayListScreenshotFiles) {
                    filePaths.append("<br>").append(arrayListScreenshotFile);
                }
                if(JOptionPane.showConfirmDialog(null, "<html>The following image files have been added:<br>" + filePaths + "<br><br>Is this correct and do you want to continue?", "Is this correct?", JOptionPane.YES_NO_OPTION) == 0){
                    GenreManager.openStepWindow(11);
                    FRAME.dispose();
                }
            }else{
                GenreManager.openStepWindow(11);
                FRAME.dispose();
            }
        });
        buttonPrevious.addActionListener(actionEvent -> {
            GenreManager.openStepWindow(9);
            FRAME.dispose();
        });
        buttonQuit.addActionListener(actionEvent -> {
            if(Utils.showConfirmDialog(1)){
                FRAME.dispose();
            }
        });
    }

    private void setGuiComponents(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 335, 160);
        setResizable(false);
        setTitle("[Page 10] Screenshots");

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel labelTitle = new JLabel("Add screenshot(s):");
        labelTitle.setBounds(98, 0, 335, 23);
        labelTitle.setForeground(Color.BLACK);
        labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(labelTitle);

        buttonAddScreenshot.setBounds(40, 30, 150, 23);
        buttonAddScreenshot.setToolTipText("<html>Click here to add image files that should be<br>used as your genre screenshots in the development progress page.");
        contentPane.add(buttonAddScreenshot);

        JLabel labelYouCanSkipThisStep = new JLabel("Note: You can skip this step if you");
        labelYouCanSkipThisStep.setBounds(15, 55,300, 23);
        contentPane.add(labelYouCanSkipThisStep);

        JLabel labelAddYourOwnGenre = new JLabel("don't want to add you own screenshots image.");
        labelAddYourOwnGenre.setBounds(15,75, 300, 23);
        contentPane.add(labelAddYourOwnGenre);

        buttonResetAddedScreenshots.setBounds(210, 30, 80, 23);
        buttonResetAddedScreenshots.setToolTipText("Click to reset all added screenshots");
        contentPane.add(buttonResetAddedScreenshots);

        buttonNext.setBounds(220, 100, 100, 23);
        buttonNext.setToolTipText("Click to continue to the next step.");
        contentPane.add(buttonNext);

        buttonPrevious.setBounds(10, 100, 100, 23);
        buttonPrevious.setToolTipText("Click to return to the previous page.");
        contentPane.add(buttonPrevious);

        buttonQuit.setBounds(120, 100, 90, 23);
        buttonQuit.setToolTipText("Click to quit this step by step guide and return to the add genre page.");
        contentPane.add(buttonQuit);
    }
}
