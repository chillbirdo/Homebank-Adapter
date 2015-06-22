package com.gf.doughflow.swing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class UIWorkingDirChooser extends JFrame {

    private final String workDir;
    
    public UIWorkingDirChooser(String defaultDir) {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a working directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(new File(defaultDir));
        int option = chooser.showOpenDialog(UIWorkingDirChooser.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            workDir = chooser.getSelectedFile().getAbsolutePath();
        } else {
            workDir = defaultDir;
        }
    }

    public void close(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    public String getWorkDir() {
        return workDir;
    }
}
