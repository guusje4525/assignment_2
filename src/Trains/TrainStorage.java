package Trains;

import Dao.dbController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainStorage {

    private List<Train> trains = new ArrayList<Train>();
    private String train;
    private String wagon;
    private int wagonseats;

    public TrainStorage() {
    }

    public void addWagon(String trainName, String wagonName){
        for(Train train : trains){
            if(train.getName().equals(trainName)) train.addWagon(new Wagon(wagonName));
        }
    }

    public void addWagon(String trainName, String wagonName, int seats){
        for(Train train : trains){
            if(train.getName().equals(trainName)) train.addWagon(new Wagon(wagonName, seats));
        }
    }

    public void addTrain(String trainName){
        //if train does not yet exist with that name
        if(trains.stream().filter(train -> train.getName().equals(trainName)).collect(Collectors.toList()).isEmpty()) trains.add(new Train(trainName));
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trainList) {
        trains = trainList;
    }

    public void loadTrains() {
        trains = dbController.getTrainsFromDB();
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

    public String getTrain() {
        return train;
    }

    public String getWagon() {
        return wagon;
    }

    public int getWagonseats() {
        return wagonseats;
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
                    dbController.deleteObject(tmpWagon);
                }
                dbController.deleteObject(train);
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
                    dbController.deleteObject(wagon);
                    return true;
                }
            }
        }
        //No wagon found
        return false;
    }

}
