package cz.mg.compilerexplorer.gui;

import cz.mg.collections.Collection;
import cz.mg.compilerexplorer.core.Node;
import javax.swing.*;


public class NodeList extends JList<Node> {
    public NodeList() {
        setLayoutOrientation(JList.VERTICAL);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new NodeListRenderer());
    }

    public void updateState(Collection<Node> state){
        setModel(new NodeListModel(state));
        if(state.count() > 0) setSelectedIndex(0);
    }
}
