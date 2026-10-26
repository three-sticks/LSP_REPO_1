package org.howard.edu.lsp.assignment5;

import java.util.Set;
import java.util.TreeSet;


public class IntegerSet {


    private final Set<Integer> elements;

    public IntegerSet() {
        elements = new TreeSet<>();
    }

    private IntegerSet copy() {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(this.elements);
        return result;
    }

    public void clear() {
        elements.clear();
    }

    public int length() {
        return elements.size();
    }

    public boolean equals(IntegerSet b) {
        if (b == null) {
            return false;
        }
        return this.elements.equals(b.elements);
    }

 
    @Override
    public boolean equals(Object o) {
        return (o instanceof IntegerSet) && equals((IntegerSet) o);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    public boolean contains(int value) {
        return elements.contains(value);
    }

    public int largest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return ((TreeSet<Integer>) elements).last();
    }

    public int smallest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return ((TreeSet<Integer>) elements).first();
    }

    public void add(int item) {
        elements.add(item);
    }

    public void remove(int item) {
        elements.remove(item);
    }

    public IntegerSet union(IntegerSet b) {
        IntegerSet result = copy();
        result.elements.addAll(b.elements);
        return result;
    }

    public IntegerSet intersect(IntegerSet b) {
        IntegerSet result = copy();
        result.elements.retainAll(b.elements);
        return result;
    }

    public IntegerSet diff(IntegerSet b) {
        IntegerSet result = copy();
        result.elements.removeAll(b.elements);
        return result;
    }

    public IntegerSet complement(IntegerSet b) {
        return b.diff(this);
    }

    public boolean isSubsetOf(IntegerSet b) {
        return b.elements.containsAll(this.elements);
    }

    public boolean isDisjoint(IntegerSet b) {
        for (int value : this.elements) {
            if (b.elements.contains(value)) {
                return false;
            }
        }
        return true;
    }

    public IntegerSet symmetricDiff(IntegerSet b) {
        IntegerSet result = this.union(b);
        result.elements.removeAll(this.intersect(b).elements);
        return result;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
