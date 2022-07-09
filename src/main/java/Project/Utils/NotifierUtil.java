package Project.Utils;


import java.util.ArrayList;
import java.util.List;

public class NotifierUtil<T> implements Notifier<T> {
    private final T subject;
    private final List<Observer<T>> observers = new ArrayList<>();

    public NotifierUtil(T subject) {
        this.subject = subject;
    }

    @Override
    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        System.out.println("notify");
        for (Observer<T> observer : observers) {
            observer.getNotified(subject);
        }
    }
}
