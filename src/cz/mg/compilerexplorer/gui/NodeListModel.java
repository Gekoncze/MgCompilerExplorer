package cz.mg.compilerexplorer.gui;

import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.compilerexplorer.core.Node;

import javax.swing.*;
import javax.swing.event.ListDataListener;


public class NodeListModel implements ListModel<Node> {
    private final Array<Node> nodes;

    public NodeListModel(Collection<Node> nodes) {
        this.nodes = new Array<>(nodes);
    }

    @Override
    public int getSize() {
        return nodes.count();
    }

    @Override
    public Node getElementAt(int i) {
        return nodes.get(i);
    }

    @Override
    public void addListDataListener(ListDataListener listDataListener) {
    }

    @Override
    public void removeListDataListener(ListDataListener listDataListener) {
    }
}
