package Models;

import Models.Cities.City;

public abstract class Production {
    private double remainedProduction;

    public Production(int remainedProduction) {
        this.remainedProduction = remainedProduction;
    }

    public Production() {

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
}
