package Project.Models;

import Project.Models.Cities.City;

public abstract class Production<T> {
    T type;
    private double remainedProduction;

    public Production(int remainedProduction) {
        this.remainedProduction = remainedProduction;
    }

    public Production(T type) {
    }

    public abstract void note(City city);

    public double getRemainedProduction() {
        return remainedProduction;
    }

    public void setRemainedProduction(double remainedProduction) {
        this.remainedProduction = remainedProduction;
    }

    public void decreaseRemainedProduction(double remainedProductions) {
        this.remainedProduction -= remainedProductions;
    }

    @Override
    public String toString() {
        return "type=" + type +
                ", remainedProduction=" + remainedProduction;
    }
}
