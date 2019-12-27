package cz.mg.compilerexplorer.gui;

import java.awt.*;


public class GridBagConstraintFactory {
    public GridBagConstraints create(int x, int y, boolean horizontalFill, boolean verticalFill, int padding){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.weightx = horizontalFill ? 1 : 0;
        constraints.weighty = verticalFill ? 1 : 0;
        constraints.insets = new Insets(padding, padding, padding, padding);
        if(!horizontalFill && !verticalFill) constraints.fill = GridBagConstraints.NONE;
        if(horizontalFill && verticalFill) constraints.fill = GridBagConstraints.BOTH;
        if(horizontalFill && !verticalFill) constraints.fill = GridBagConstraints.HORIZONTAL;
        if(!horizontalFill && verticalFill) constraints.fill = GridBagConstraints.VERTICAL;
        return constraints;
    }
}
