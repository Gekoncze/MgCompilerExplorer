package cz.mg.compilerexplorer.gui;

import cz.mg.compiler.Compiler;
import cz.mg.compilerexplorer.core.Explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainWindow extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int PADDING = 8;
    public static final String TITLE = "Compiler Explorer";

    private final Compiler compiler;
    private final TaskExplorer taskExplorer;
    private final EntityExplorer entityExplorer;
    private final GridBagConstraintFactory constraintFactory = new GridBagConstraintFactory();

    private final WindowAdapter windowAdapter = new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
        }
    };

    public MainWindow(Compiler compiler) {
        this.compiler = compiler;

        setupComponent();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        getContentPane().add(splitPane, constraintFactory.create(0, 0, true, true, 0));

        this.taskExplorer = new TaskExplorer(compiler.getTasks());
        this.entityExplorer = new EntityExplorer(compiler.getEntities());
        taskExplorer.setEntityExplorer(entityExplorer);
        entityExplorer.setTaskExplorer(taskExplorer);
        splitPane.setLeftComponent(taskExplorer);
        splitPane.setRightComponent(entityExplorer);

        addListeners();
    }

    private void setupComponent(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridBagLayout());
    }

    private void addListeners(){
        addWindowListener(windowAdapter);
    }

//    private void addGlobalKeyListeners(Component component){
//        component.addKeyListener(globalKeyAdapter);
//        if(component instanceof Container){
//            for(Component child : ((Container)component).getComponents()) {
//                addGlobalListeners(child);
//            }
//        }
//    }
}
