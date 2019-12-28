package cz.mg.compilerexplorer.gui;

import cz.mg.compiler.entities.Entity;
import cz.mg.compiler.tasks.Task;
import cz.mg.compiler.tasks.Tasks;
import cz.mg.compilerexplorer.core.Node;


public class TaskExplorer extends NodeExplorer {
    private EntityExplorer entityExplorer = null;

    public TaskExplorer(Tasks tasks) {
        super(new Node("", tasks));
    }

    public void setEntityExplorer(EntityExplorer entityExplorer) {
        this.entityExplorer = entityExplorer;
    }

    @Override
    public void openLink(Node node) {
        if(node.getElement() instanceof Entity){
            entityExplorer.openLink(node);
        } else if(node.getElement() instanceof Task){
            Node target = explorer.getLinkTarget(node);
            if(target != null) node = target;
            open(node);
        }
    }
}
