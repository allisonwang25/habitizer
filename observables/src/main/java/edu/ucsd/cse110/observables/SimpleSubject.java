package edu.ucsd.cse110.observables;

import androidx.annotation.Nullable;

public interface SimpleSubject<T> {
    @Nullable
    T getValue();

    void observe(Observer<T> observer);

    void removeObserver(Observer<T> observer);
}
