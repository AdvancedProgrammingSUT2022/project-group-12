package Project.Utils;

public interface Notifier {
    void addObserver(TileObserver observer);
    void notifyObservers();
}
