package cz.mg.compilerexplorer.gui;

import cz.mg.compilerexplorer.core.Node;
import javax.swing.*;
import java.awt.*;


public class NodeListRenderer extends JLabel implements ListCellRenderer<Node> {
    public NodeListRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Node> list, Node node, int i, boolean isSelected, boolean cellHasFocus) {
        if (isSelected && list.hasFocus()) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setText(nodeToString(node));
        return this;
    }

    public static String nodeToString(Node node) {
        return node.getElement().getClass().getSimpleName() + " " + node.getName();
    }
}
