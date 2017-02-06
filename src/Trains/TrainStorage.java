package Trains;

import Dao.DatabaseManager;
import Dao.SQLLiteManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainStorage {

    private List<Train> trains = new ArrayList<Train>();
    private DatabaseManager database;

    public TrainStorage() {
        database = new SQLLiteManager();
    }

    public void addWagon(String trainName, String wagonName){
        for(Train train : trains){
            if(train.getName().equals(trainName)){
                Wagon newWagon = new Wagon(wagonName);
                newWagon.setTrainName(trainName);
                train.addWagon(newWagon);
                database.addObject(newWagon);
            }
        }
    }

    public void addWagon(String trainName, String wagonName, int seats){
        for(Train train : trains){
            if(train.getName().equals(trainName)){
                Wagon newWagon = new Wagon(wagonName, seats);
                newWagon.setTrainName(trainName);
                train.addWagon(newWagon);
                database.addObject(newWagon);
            }
        }
    }

    public void addTrain(String trainName){
        //if train does not yet exist with that name
        if(trains.stream().filter(train -> train.getName().equals(trainName)).collect(Collectors.toList()).isEmpty()){
            Train newTrain = new Train(trainName);
            trains.add(newTrain);
            database.addObject(newTrain);
        }
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trainList) {
        trains = trainList;
    }

    public void loadTrains() {
        trains = database.getTrains();
    }

    public boolean trainExist(String trainName) {
        for(Train train : trains) if(train.getName().equals(trainName)) return true;
        return false;
    }

    public boolean wagonExist(String wagonName) {
        for (Train train : trains) {
            for (Wagon wagon : train.getWagons()) {
                if (wagon.getWagon().equals(wagonName)) return true;
            }
        }
        return false;
    }

    public int getWagonSeats(String wagonName) {
        for(Train train : trains){
            for(Wagon wagon : train.getWagons()){
                if(wagon.getWagon().equals(wagonName)) return wagon.getSeats();
            }
        }
        //No wagon found, fallback, controller will catch this
        return -1;
    }

    public String getTrainSeats(String trainName) {
        for(Train train : trains){
            if(train.getName().equals(trainName)) return "" + train.getSeats();
        }
        return "No train found";
    }

    public int getAllSeats() {
        int totalSeats = 0;
        for(Train train : trains){
            totalSeats += train.getSeats();
        }
        return totalSeats;
    }

    public boolean deleteTrain(String trainName) {
        for (Iterator<Train> iter = trains.listIterator(); iter.hasNext(); ) {
            Train train = iter.next();
            if (train.getName().equals(trainName)) {
                iter.remove();
                for (Wagon tmpWagon : train.getWagons()) {
                    database.deleteObject(tmpWagon);
                }
                database.deleteObject(train);
                return true;
            }
        }
        //No train found
        return false;
    }

    public boolean deleteWagon(String wagonName) {

        for(Train train : trains){
            for (Iterator<Wagon> iter = train.getWagons().listIterator(); iter.hasNext(); ) {
                Wagon wagon = iter.next();
                if (wagon.getWagon().equals(wagonName)) {
                    iter.remove();
                    database.deleteObject(wagon);
                    return true;
                }
            }
        }
        //No wagon found
        return false;
    }

}
