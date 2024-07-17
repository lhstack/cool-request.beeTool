package com.lhstack.panel;

import javax.swing.*;
import java.awt.*;

public class GridLayoutPanel extends JPanel {

    public GridLayoutPanel(int rows,int cols) {
        this.setLayout(new GridLayout(rows,cols));
    }

    public GridLayoutPanel adds(Component ...components){
        for (Component component : components) {
            super.add(component);
        }
        return this;
    }
}
