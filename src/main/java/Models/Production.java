package Models;

import Models.Cities.City;

public abstract class Production {
    private int remainedProductions;

    public abstract void note(City city);

    public Production(int setProduction) {
        this.remainedProductions = setProduction;
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
