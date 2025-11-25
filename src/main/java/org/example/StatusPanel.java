package org.example;

import javax.swing.*;
import java.awt.*;

abstract class StatusPanel extends JPanel {
    public StatusPanel(){
        super();
    }
    public abstract void updateFromSnapshot(EnvironmentSnapshot snapshot);


    protected void applyWarningBackground(JPanel, boolean warning){
        if (warning){
            panel.setBackground(Color.RED);
        }else {
            panel.setBackground(UIManager.getColor("Panel background"));
        }
    }
}
