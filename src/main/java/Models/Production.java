package Models;

import Models.Cities.City;

public abstract class Production {
    private int remainedProductions;

    public abstract void note(City city);

    public Production(int setProduction) {
        this.remainedProductions = 0;
    }

    public Production() {

    }

    public int getRemainedProductions() {
        return remainedProductions;
    }

    public void setRemainedProductions(int remainedProductions) {
        this.remainedProductions += remainedProductions;
    }
}
