package edu.ucsd.cse110.habitizer.app.util;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.Collections;
import java.util.List;

import edu.ucsd.cse110.observables.Observer;
import edu.ucsd.cse110.observables.Subject;

public class LiveDataSubjectAdapter <T> implements Subject<T> {
    private final LiveData<T> adaptee;

    public LiveDataSubjectAdapter(LiveData<T> adaptee) {
        this.adaptee = adaptee;
    }

    @Nullable
    @Override
    public T getValue() {
        return adaptee.getValue();
    }

    @Override
    public boolean hasObservers() {
        return false;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public Observer<T> observe(Observer<T> observer) {
        adaptee.observeForever(observer::onChanged);
        return observer;
    }

    @Override
    public void removeObserver(Observer<T> observer) {
        adaptee.removeObserver(observer::onChanged);
    }

    @Override
    public void removeObservers() {

    }

    @Override
    public List<Observer<T>> getObservers() {
        return Collections.emptyList();
    }
}
