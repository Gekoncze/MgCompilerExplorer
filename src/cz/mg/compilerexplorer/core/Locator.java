package cz.mg.compilerexplorer.core;

import cz.mg.collections.Collection;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.compiler.annotations.Info;
import cz.mg.compiler.annotations.Link;
import cz.mg.compiler.annotations.Part;
import cz.mg.utilities.ReflectionUtilities;

import java.lang.reflect.Field;


public class Locator {
    private final NodeCache parentCache = new NodeCache();
    private final NodeCache targetCache = new NodeCache();
    private final Node root;

    public Locator(Node root) {
        this.root = root;
    }

    public Node findParent(Node child){
        if(child.equals(root)) return null;
        if(parentCache.hasKey(child)) return parentCache.get(child);
        return searchParentRecursive(root, child);
    }

    public Node findLinkTarget(Node link){
        if(link.equals(root)) return root;
        if(targetCache.hasKey(link)) return targetCache.get(link);
        return searchLinkTargetRecursive(root, link);
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
        parentCache.clear();
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

    private Node searchParentRecursive(Node parent, Node wanted){
        for(Node child : searchNodes(parent, Part.class)){
            if(child.equals(wanted)){
                parentCache.put(child, parent);
                return parent;
            }
            Node result = searchParentRecursive(child, wanted);
            if(result != null){
                parentCache.put(child, parent);
                return result;
            }
        }
        return null;
    }

    private Node searchLinkTargetRecursive(Node parent, Node wanted){
        for(Node child : searchNodes(parent, Part.class)){
            if(child.equals(wanted)){
                parentCache.put(child, parent);
                targetCache.put(wanted, child);
                return child;
            }
            Node result = searchLinkTargetRecursive(child, wanted);
            if(result != null){
                parentCache.put(child, parent);
                return result;
            }
        }
        return null;
    }
}
