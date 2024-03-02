package model.adt;

public interface MyIList<T>{
    void add(T value);

    boolean isEmpty();

    T get(int i);

    int getSize();
}
