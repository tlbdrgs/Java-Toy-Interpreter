package model.adt;

import exceptions.MyException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyLockTable implements MyILockTable{

    Map<Integer, Integer> lockTable;

    int freeLocation;

    public MyLockTable(){
        lockTable = new ConcurrentHashMap<>();
        freeLocation = 0;
    }

    public int getFreeLocation() {
        synchronized (this){
            freeLocation++;
            return freeLocation;
        }
    }

    public void put(int key, int value) throws MyException {
        if(!isDefined(key))
            lockTable.put(key, value);
        else throw new MyException("Lock table already contains the given key!");

    }

    public Map<Integer, Integer> getContent() {
        synchronized (this){
            return lockTable;
        }
    }

    public boolean isDefined(int position) {
        return lockTable.containsKey(position);
    }

    public int get(int position) throws MyException {
        if(!isDefined(position))
            throw new MyException("The position: " + position + " is not present in the lock table!");
        return lockTable.get(position);
    }

    public void update(int position, int value) throws MyException {
        if(!isDefined(position))
            throw new MyException("The position: " + position + " is not present in the lock table!");
        lockTable.replace(position, value);

    }


    public void setContent(Map<Integer, Integer> newMap) {
        synchronized (this){
            this.lockTable = newMap;
        }
    }


    public Set<Integer> keySet() {
       synchronized (this){
           return lockTable.keySet();
       }
    }

    public String toString(){
        String string = "";
        for (Map.Entry<Integer, Integer> s : lockTable.entrySet()) {
            string += (s.getKey() + " --> " + s.getValue()) + "\n";
        }
        return string;
    }
}
