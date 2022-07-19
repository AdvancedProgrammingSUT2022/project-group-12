package Project.SharedUtils;


import Project.SharedModels.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class NotifierUtil implements Notifier {
    private final Tile subject;
    private final String type;
    private final List<TileObserver> observers = new ArrayList<>();

    public NotifierUtil(Tile subject) {
        this.subject = subject;
        this.type = this.getClass().getName();
    }

    @Override
    public void addObserver(TileObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
//        System.out.println("subject = " + subject);;
//        System.out.println("notify");
        for (TileObserver observer : observers) {
            observer.getNotified(subject);
        }
    }
}
