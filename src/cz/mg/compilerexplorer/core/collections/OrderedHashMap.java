package cz.mg.compilerexplorer.core.collections;

import cz.mg.collections.list.ReadonlyList;
import cz.mg.collections.list.chainlist.ChainList;

import java.util.Collection;
import java.util.HashMap;


public class OrderedHashMap<A, B> {
    private final ChainList<A> keys = new ChainList<>();
    private final HashMap<A, B> map = new HashMap<>();

    public void put(A a, B b){
        keys.remove(a);
        keys.addLast(a);
        map.put(a, b);
    }

    public int count(){
        return keys.count();
    }

    public B get(A a){
        return map.get(a);
    }

    public void remove(A a){
        keys.remove(a);
        map.remove(a);
    }

    public void clear(){
        keys.clear();
        map.clear();
    }

    public ReadonlyList<A> getKeys() {
        return keys;
    }

    public Collection<B> getValues(){
        return map.values();
    }

    public boolean hasKey(A key){
        return map.containsKey(key);
    }
}
