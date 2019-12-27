package cz.mg.compilerexplorer.gui;

import cz.mg.compilerexplorer.core.Explorer;
import cz.mg.compilerexplorer.core.Node;
import cz.mg.compilerexplorer.core.SelectedChildCache;
import cz.mg.compilerexplorer.core.State;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public abstract class NodeExplorer extends JPanel {
    public static final int PADDING = 8;
    public static final int SELECTED_INDEX_ID = 0;

    private final GridBagConstraintFactory constraintFactory = new GridBagConstraintFactory();
    private final SelectedChildCache cache = new SelectedChildCache();

    protected final JTextField path;
    protected final NodeList listOfParts = new NodeList();
    protected final NodeList listOfInfos = new NodeList();
    protected final NodeList listOfLinks = new NodeList();
    protected final Explorer explorer;

    public NodeExplorer(Node root) {
        this.explorer = new Explorer(root);

        setLayout(new GridBagLayout());

        this.path = new JTextField();
        this.path.setEditable(false);
        add(path, constraintFactory.create(0, 0, true, false, PADDING));

        JSplitPane horizontalSplitPane = new JSplitPane();
        horizontalSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplitPane.setResizeWeight(0.5);

        JSplitPane verticalSplitPane = new JSplitPane();
        verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setResizeWeight(0.5);

        add(horizontalSplitPane, constraintFactory.create(0, 1, true, true, PADDING));
        horizontalSplitPane.setRightComponent(verticalSplitPane);

        horizontalSplitPane.setLeftComponent(listOfParts);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.add(new JLabel("Properties"), constraintFactory.create(0, 0, true, false, PADDING));
        topPanel.add(listOfInfos, constraintFactory.create(0, 1, true, true, PADDING));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.add(new JLabel("References"), constraintFactory.create(0, 0, true, false, PADDING));
        bottomPanel.add(listOfLinks, constraintFactory.create(0, 1, true, true, PADDING));

        verticalSplitPane.setLeftComponent(topPanel);
        verticalSplitPane.setRightComponent(bottomPanel);

        listOfParts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if(!lsm.isSelectionEmpty()){
                    saveSelectedIndex(lsm.getMinSelectionIndex());
                }
            }
        });

        listOfParts.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    open(listOfParts.getSelectedValue());
                }

                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    openParent();
                }

                if(e.getKeyCode() == KeyEvent.VK_LEFT && e.isAltDown()){
                    back();
                }

                if(e.getKeyCode() == KeyEvent.VK_RIGHT && e.isAltDown()){
                    forward();
                }
            }
        });

        listOfLinks.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    openLink(listOfLinks.getSelectedValue());
                }
            }
        });

        updateState();
    }

    public void open(Node node){
        explorer.open(node);
        updateState();
    }

    public void openParent(){
        explorer.openParent();
        updateState();
    }

    public abstract void openLink(Node node);

    public void back(){
        explorer.back();
        updateState();
    }

    public void forward(){
        explorer.forward();
        updateState();
    }

    private void updatePath(){
        path.setText(explorer.getPath().toString("/", Node::getName) + "/");
    }

    private void updateState(){
        try {
            saveSelectedIndexLock = true;
            State state = explorer.getState();
            listOfParts.updateState(state.getParts());
            listOfInfos.updateState(state.getInfos());
            listOfLinks.updateState(state.getLinks());
            listOfParts.setSelectedIndex(loadSelectedIndex());
            updatePath();
        } finally {
            saveSelectedIndexLock = false;
        }
    }

    private boolean saveSelectedIndexLock = false;
    private void saveSelectedIndex(int index){
        if(saveSelectedIndexLock == true) return;
        cache.put(explorer.get(), index);
    }

    private int loadSelectedIndex(){
        Object data = cache.get(explorer.get());
        if(data == null) return 0;
        return (int) data;
    }
}
