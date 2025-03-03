package edu.ucsd.cse110.habitizer.app.util;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import edu.ucsd.cse110.observables.Observer;
import edu.ucsd.cse110.observables.SimpleSubject;
import edu.ucsd.cse110.observables.Subject;

public class LiveDataSubjectAdapter <T> implements SimpleSubject<T> {
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
    public void observe(Observer<T> observer) {
        adaptee.observeForever(observer::onChanged);
    }

    @Override
    public void removeObserver(Observer<T> observer) {
        adaptee.removeObserver(observer::onChanged);
    }
}
