package cz.mg.compilerexplorer.core;

import cz.mg.collections.list.chainlist.ChainList;


public class Explorer {
    private final History history;
    private final Locator locator;

    public Explorer(Node root) {
        this.history = new History(root);
        this.locator = new Locator(root);
    }

    public History getHistory() {
        return history;
    }

    public State getState(){
        return new State(
                locator.findChildren(history.get()),
                locator.findProperties(history.get()),
                locator.findReferences(history.get())
        );
    }

    public Node get(){
        return history.get();
    }

    public void open(Node node){
        history.open(node);
    }

    public void back(){
        history.back();
    }

    public void forward(){
        history.forward();
    }

    public void openParent(){
        history.open(locator.findParent(get()));
    }

    public ChainList<Node> getPath(){
        ChainList<Node> path = new ChainList<>();
        Node node = get();
        while(node != null){
            path.addFirst(node);
            node = locator.findParent(node);
        }
        return path;
    }
}
