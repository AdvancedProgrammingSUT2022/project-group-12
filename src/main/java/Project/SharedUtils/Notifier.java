package Project.SharedUtils;

public interface Notifier {
    void addObserver(TileObserver observer);
    void notifyObservers();
}
