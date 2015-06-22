package com.gf.doughflow.swing;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class UIHandler extends JFrame {

    public UIHandler() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
    }
    
    public String showDirChooser(String defaultDir){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a working directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(new File(defaultDir));
        int option = chooser.showOpenDialog(UIHandler.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return defaultDir;
        }        
    } 
    

    public void showImportedMessage(String msg) {
        JOptionPane.showMessageDialog(this,
                msg);
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
