package cz.mg.compilerexplorer.core;

import cz.mg.compilerexplorer.core.collections.CacheMap;


public class ParentChildCache extends CacheMap<Node, Node> {
    private static final int DEFAULT_LIMIT = 1000;

    public ParentChildCache() {
        super(DEFAULT_LIMIT);
    }

    public ParentChildCache(int limit) {
        super(limit);
    }
}
