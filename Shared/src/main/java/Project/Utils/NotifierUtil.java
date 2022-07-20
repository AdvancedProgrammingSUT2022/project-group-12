package Project.Utils;


import Project.Models.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class NotifierUtil implements Notifier {
    private final Tile subject;
    private final List<TileObserver> observers = new ArrayList<>();

    public NotifierUtil(Tile subject) {
        this.subject = subject;
    }

    @Override
    public void addObserver(TileObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
//        System.out.println("subject = " + subject);;
        for (TileObserver observer : observers) {
            observer.getNotified(subject);
        }
    }
}
