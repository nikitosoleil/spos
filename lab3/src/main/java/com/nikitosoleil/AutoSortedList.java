package com.nikitosoleil;

import java.util.*;

public class AutoSortedList<T extends Comparable> implements List<T> {
    private List<T> list;

    private AutoSortedList() {

    }

    public AutoSortedList(Vector<T> initValues) {
        list = new ArrayList<>();
        for (T val : initValues) {
            list.add(val);
        }
        resort();
    }

    public void resort(){
        Collections.sort(list);
    }
    
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return list.toArray(t1s);
    }

    @Override
    public boolean add(T t) {
        boolean resBool = list.add(t);
        resort();
        return resBool;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean resBool = list.addAll(collection);
        resort();
        return resBool;
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        boolean resBool = list.addAll(i, collection);
        resort();
        return resBool;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean resBool = list.retainAll(collection);
        resort();
        return resBool;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int i) {
        return list.get(i);
    }

    @Override
    public T set(int i, T t) {
        T res = list.set(i, t);
        resort();
        return res;
    }

    @Override
    public void add(int i, T t) {
        list.add(i, t);
        resort();
    }

    @Override
    public T remove(int i) {
        return list.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return list.listIterator(i);
    }

    @Override
    public List<T> subList(int i, int i1) {
        return list.subList(i, i1);
    }
}
