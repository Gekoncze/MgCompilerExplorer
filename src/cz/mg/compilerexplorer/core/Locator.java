package cz.mg.compilerexplorer.core;

import cz.mg.collections.Collection;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.compiler.annotations.Info;
import cz.mg.compiler.annotations.Link;
import cz.mg.compiler.annotations.Part;
import cz.mg.utilities.ReflectionUtilities;

import java.lang.reflect.Field;


public class Locator {
    private final ParentChildCache cache;
    private final Node root;

    public Locator(Node root) {
        this.root = root;
        this.cache = new ParentChildCache();
    }

    public Node findParent(Node child){
        if(child.equals(root)) return null;
        if(cache.hasKey(child)) return cache.get(child);
        return searchNodeParentRecursive(root, child);
    }

    public Collection<Node> findChildren(Node parent){
        return searchNodes(parent, Part.class);
    }

    public Collection<Node> findProperties(Node parent){
        return searchNodes(parent, Info.class);
    }

    public Collection<Node> findReferences(Node parent){
        return searchNodes(parent, Link.class);
    }

    public void clearCache(){
        cache.clear();
    }

    private Collection<Node> searchNodes(Node parent, Class annotation) {
        Object element = parent.getElement();
        ChainList<Node> nodes = new ChainList();
        for (Field field : ReflectionUtilities.getObjectFields(element)) {
            Object value = ReflectionUtilities.readObjectField(element, field);
            String name = field.getName();
            if(value != null){
                boolean include = field.isAnnotationPresent(annotation);
                if(include){
                    if(value instanceof Iterable){
                        int i = 0;
                        for(Object object : (Iterable)value){
                            nodes.addLast(new Node(name + "[" + i + "]", object));
                            i++;
                        }
                    } else {
                        nodes.addLast(new Node(name, value));
                    }
                }
            }
        }
        return nodes;
    }

    private Node searchNodeParentRecursive(Node parent, Node wanted){
        for(Node child : searchNodes(parent, Part.class)){
            if(child.equals(wanted)){
                cache.put(child, parent);
                return parent;
            }
            Node result = searchNodeParentRecursive(child, wanted);
            if(result != null){
                cache.put(child, parent);
                return result;
            }
        }
        return null;
    }
}
