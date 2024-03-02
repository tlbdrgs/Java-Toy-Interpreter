package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    final List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        synchronized (list) {
            list.add(value);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    @Override
    public String toString() {
        return list.stream().map(Object::toString).reduce("", (partialString, element) -> partialString + element + "\n");
    }

    @Override
    public T get(int i) {
        synchronized (list){
            return list.get(i);
        }
    }

    @Override
    public int getSize() {
        synchronized (list) {
            return list.size();
        }
    }
}
