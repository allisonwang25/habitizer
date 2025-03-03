package edu.ucsd.cse110.observables;

public interface SimpleMutableSubject<T> extends SimpleSubject<T> {

    void setValue(T value);

}
