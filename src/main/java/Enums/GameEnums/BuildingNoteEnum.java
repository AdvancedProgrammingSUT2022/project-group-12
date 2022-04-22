package Enums.GameEnums;

import Models.Buildings.BuildingNotes;
import Models.Cities.City;

public enum BuildingNoteEnum {
    BARRACK_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
        }
    }),
    GRANARY_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setFood(city.getFood() + 2);
        }
    }),
    LIBRARY_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setBeaker(city.getCitizensCount() / 2 + city.getBeaker());
        }
    }),
    MONUMENT_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            return;
        }
    }),
    WALLS_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
        }
    }),
    WALLTERMILLS_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
            //if border a river --> city.setFood(city.getFood()+2);
        }
    }),
    ARMORY_NOTE(new BuildingNotes() {
        @Override
        public void note(Object o) {
            //TODO : complete
        }
    }),
    BURIAL_TOMB_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setHappiness(city.getHappiness() + 2);
            //TODO : complete
        }
    }),
    CIRCUS_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setHappiness(city.getHappiness() + 3);
            //TODO : complete
        }
    }),
    COLOSSEUM_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setHappiness(city.getHappiness() + 4);
        }
    }),
    COURTHOUSE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
        }
    }),
    STABLE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
        }
    }),
    TEMPLE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
        }
    }),
    CASTLE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setCombatStrength(city.getCombatStrength() + 7.5);
        }
    }),
    FORGE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
            city.setProduction(city.getProduction() + (city.getProduction() * 15.0 / 100.0));
        }
    }),
    GARDEN_NOTE(new BuildingNotes() {
        @Override
        public void note(Object o) {
            //TODO : complete
        }
    }),
    MARKET_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setGold(city.getGold() + city.getGold() / 4);
        }
    }),
    MINT_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City o) {
            //TODO : complete
        }
    }),
    MONASTERY_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
        }
    }),
    UNIVERSITY_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            //TODO : complete
            city.setBeaker(city.getBeaker() + city.getBeaker() / 2);
        }
    }),
    WORKSHOP_NOTE(new BuildingNotes() {
        @Override
        public void note(Object o) {
            //TODO : complete
        }
    }),
    BANK(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setGold(city.getGold() + city.getGold() / 2);
        }
    }),
    MILITARY_ACADEMY_NOTE(new BuildingNotes() {
        @Override
        public void note(Object o) {
            //TODO : complete
        }
    }),
    MUSEUM_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
        }
    }),
    OPERA_HOUSE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
        }
    }),
    PUBLIC_SCHOOL_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setBeaker(city.getBeaker() + city.getBeaker() / 2);
        }
    }),
    SATRAPS_COURT_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setGold(city.getGold() + city.getGold() / 4);
            city.setHappiness(city.getHappiness() + 2);
        }
    }),
    THEATER_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setHappiness(city.getHappiness() + 4);
        }
    }),
    WINDMILL_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setProduction(city.getProduction() + city.getProduction() * 15.0 / 100.0);
        }
    }),
    ARSENAL_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setProduction(city.getProduction() + city.getProduction() * 20.0 / 100.0);
        }
    }),
    BROADCAST_TOWER_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
        }
    }),
    FACTORY_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setProduction(city.getProduction() + city.getProduction() / 2);
        }
    }),
    HOSPITAL_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setFood(city.getFood() - city.getFood() / 2);
        }
    }),
    MILITARY_BASE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setCombatStrength(city.getCombatStrength() + 12);
        }
    }),
    STOCK_EXCHANGE_NOTE(new BuildingNotes<City>() {
        @Override
        public void note(City city) {
            city.setGold(city.getGold() + city.getGold() / 3);
        }
    });


    private final BuildingNotes note;

    BuildingNoteEnum(BuildingNotes note) {
        this.note = note;
    }

    public BuildingNotes getNote() {
        return note;
    }
}
