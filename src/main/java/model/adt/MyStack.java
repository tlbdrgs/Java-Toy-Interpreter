package model.adt;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {

    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public List<T> reversedStackListForm() {
        List<T> reversedStackList = new ArrayList<>(stack);
        Collections.reverse(reversedStackList);
        return reversedStackList;
    }

    @Override
    public String toString() {

        String result = stack.stream().map(Object::toString).reduce((acc, item) -> item + "\n" + acc).orElse("");

        if(isEmpty())
            return result;

        return result + "\n";

    }
}
