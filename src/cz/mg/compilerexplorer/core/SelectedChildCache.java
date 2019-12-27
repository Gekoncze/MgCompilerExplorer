package cz.mg.compilerexplorer.core;

import cz.mg.compilerexplorer.core.collections.CacheMap;


public class SelectedChildCache extends CacheMap<Node, Integer> {
    private static final int DEFAULT_LIMIT = 1000;

    public SelectedChildCache() {
        super(DEFAULT_LIMIT);
    }

    public SelectedChildCache(int limit) {
        super(limit);
    }
}
