package com.bzh.sportrecord.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/26 10:14
 */
public class MyList<E> implements List<E> {

    private List<E> list;
    private addAndRemoveListener<E> addAndRemoveListener;

    public MyList(List<E> list) {
        this.list = list;
    }

    public void setAddAndRemoveListener(MyList.addAndRemoveListener<E> addAndRemoveListener) {
        this.addAndRemoveListener = addAndRemoveListener;
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
    public boolean contains(@Nullable Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Nullable
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(@Nullable T[] a) {
        return list.toArray(a);
    }

    //增添数据的监听
    @Override
    public boolean add(E e) {
        addAndRemoveListener.run(this);
        return list.add(e);
    }

    //移除数据的监听
    @Override
    public boolean remove(@Nullable Object o) {
        addAndRemoveListener.run(this);
        return list.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return list.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    //增添和删除的回调
    public interface addAndRemoveListener<E>{
        void run(List<E> es);
    }
}
