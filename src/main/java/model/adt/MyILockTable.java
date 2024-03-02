package model.adt;

import exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyILockTable {
    int getFreeLocation();
    void put(int key, int value) throws MyException;
    Map<Integer, Integer> getContent();
    boolean isDefined(int position);
    int get(int position) throws MyException;
    void update(int position, int value) throws MyException;
    void setContent(Map<Integer, Integer> newMap);
    Set<Integer> keySet();
}
