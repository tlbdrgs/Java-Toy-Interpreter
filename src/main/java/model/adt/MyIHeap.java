package model.adt;

import model.values.IValue;

import java.util.Map;

public interface MyIHeap<K, V> {


    Map<K, V> getContent();

    K getFreeAddress();

    void setContent(Map<K, V> newHeap);

    boolean isDefined(K id);

    V lookup(K id);

    void allocate(V val);

    void update(K id, V val);

    Iterable<Map.Entry<K,V>> getIterableSet();

    void remove(K id);
}
