package cz.mg.compilerexplorer.gui;

import cz.mg.compiler.entities.Entities;
import cz.mg.compiler.entities.Entity;
import cz.mg.compiler.tasks.Task;
import cz.mg.compilerexplorer.core.Node;


public class EntityExplorer extends NodeExplorer {
    private TaskExplorer taskExplorer = null;

    public EntityExplorer(Entities entities) {
        super(new Node("", entities));
    }

    public void setTaskExplorer(TaskExplorer taskExplorer) {
        this.taskExplorer = taskExplorer;
    }

    @Override
    public void openLink(Node node) {
        if(node.getElement() instanceof Task){
            taskExplorer.open(node);
        } else if(node.getElement() instanceof Entity) {
            open(node);
        }
    }
}
