package cz.mg.compilerexplorer.core.collections;

public class CacheMap<A, B> extends OrderedHashMap<A, B> {
    private final int limit;

    public CacheMap(int limit) {
        this.limit = limit;
    }

    @Override
    public void put(A a, B b){
        super.put(a, b);
        if(count() > limit) remove(getKeys().getFirst());
    }
}
