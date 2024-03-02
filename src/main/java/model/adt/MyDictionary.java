package model.adt;

import exceptions.MyException;

import java.util.Map;
import java.util.HashMap;
public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    Map<K, V> dictionary;

    public MyDictionary(){
        this.dictionary = new HashMap<>();
    }

    @Override
    public V lookup(K id) throws MyException {
        if(isDefined(id))
            return dictionary.get(id);
        throw new MyException("Variable not defined!");

        /// return dictionary.get(id);
    }

    @Override
    public boolean isDefined(K id) {
        return dictionary.containsKey(id);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public void update(K id, V value) {
        dictionary.put(id, value);
    }

    @Override
    public String toString() {
        return dictionary.entrySet().stream().map(Object::toString).reduce("", (partialString, element) -> partialString + element + "\n");
    }
    @Override
    public Iterable<Map.Entry<K, V>> getIterableSet() {
        return dictionary.entrySet();
    }

    @Override
    public void remove(K id) {
        dictionary.remove(id);
    }

    public MyIDictionary<K, V> copy(){
        MyIDictionary<K, V> newDict = new MyDictionary<>();

        for(K key : dictionary.keySet())
            newDict.update(key, dictionary.get(key));

        return newDict;
    }
}
