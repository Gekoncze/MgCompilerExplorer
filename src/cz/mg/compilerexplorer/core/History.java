package cz.mg.compilerexplorer.core;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.chainlist.ChainListItem;


public class History {
    private static final int MAX_HISTORY = 100;

    private final ChainList<Node> path = new ChainList<>();
    private ChainListItem<Node> currentItem;

    public History(Node node) {
        if(node == null) throw new IllegalArgumentException();
        path.addLast(node);
        currentItem = path.getFirstItem();
    }

    public ChainList<Node> getPath() {
        return path;
    }

    public void open(Node node){
        if(node == null) return;
        if(node == currentItem.getData()) return;
        while(currentItem.hasNext()) currentItem.removeNext();
        path.addLast(node);
        currentItem = path.getLastItem();
        trim();
    }

    public void back(){
        if(currentItem.getPreviousItem() != null) currentItem = currentItem.getPreviousItem();
    }

    public void forward(){
        if(currentItem.getNextItem() != null) currentItem = currentItem.getNextItem();
    }

    public Node get(){
        return currentItem.getData();
    }

    private void trim(){
        while(path.count() > 0 && path.count() > MAX_HISTORY){
            path.removeFirst();
        }
    }
}
