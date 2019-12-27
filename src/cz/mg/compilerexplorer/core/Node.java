package cz.mg.compilerexplorer.core;


public class Node {
    private final String name;
    private final Object element;

    public Node(String name, Object element) {
        this.name = name;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public Object getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Node)) return false;
        Node node = (Node) o;
        return node.getElement() == getElement();
    }

    @Override
    public int hashCode() {
        return element.hashCode();
    }
}