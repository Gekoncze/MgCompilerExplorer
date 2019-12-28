package cz.mg.compilerexplorer.core;

import cz.mg.compilerexplorer.core.collections.CacheMap;


public class NodeCache extends CacheMap<Node, Node> {
    private static final int DEFAULT_LIMIT = 1000;

    public NodeCache() {
        super(DEFAULT_LIMIT);
    }

    public NodeCache(int limit) {
        super(limit);
    }
}
