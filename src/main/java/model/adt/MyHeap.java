package model.adt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap<V> implements MyIHeap<Integer, V>{

    Map<Integer, V> heap;
    Integer freeAddress;

    public  MyHeap(){
        this.heap = new ConcurrentHashMap<>();
        this.freeAddress = 1;
    }

    public Integer getFreeAddress() {
        return freeAddress;
    }

    @Override
    public Map<Integer, V> getContent() {
        synchronized (this){
            return heap;
        }
    }

    @Override
    public void setContent(Map<Integer, V> newHeap) {
        synchronized (this){
            heap = newHeap;
        }
    }

    @Override
    public boolean isDefined(Integer id) {
        return heap.containsKey(id);
    }

    @Override
    public V lookup(Integer id) { return heap.get(id); }

    @Override
    public void allocate(V val) {
        synchronized (this){
            heap.put(freeAddress, val);
            freeAddress++;
        }
    }

    @Override
    public void update(Integer id, V val) {
        heap.put(id, val);
    }

    @Override
    public Iterable<Map.Entry<Integer, V>> getIterableSet() {
        return heap.entrySet();
    }

    @Override
    public void remove(Integer id) {
        heap.remove(id);
    }

    @Override
    public String toString() {
        String string = "";
        for (Map.Entry<Integer, V> s : heap.entrySet()) {
            string += (s.getKey() + " --> " + s.getValue()) + "\n";
        }
        return string;
    }
}
