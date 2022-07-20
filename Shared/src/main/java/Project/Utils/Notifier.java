package Project.Utils;

public interface Notifier<T> {
    void addObserver(Observer<T> observer);
    void notifyObservers();
}
