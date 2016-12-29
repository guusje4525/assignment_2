package Trains;

import Dao.dbController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class TrainStorage extends Observable {

    private List<Train> trains = new ArrayList<Train>();
    private String train;
    private String wagon;
    private int wagonseats;

    public TrainStorage() {
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

    public void trainChanged() {
        setChanged();
        notifyObservers();
    }

    public void updateTrains(String traint, String wagont, int wagonseats) {
        this.train = traint;
        this.wagon = wagont;
        this.wagonseats = wagonseats;

        if (trains.stream().filter(train -> train.getName().equals(traint)).collect(Collectors.toList()).isEmpty()) {
            Train newTrain = new Train(train);
            trains.add(newTrain);
            dbController.addObject(newTrain);
        }

        if (wagont != null && wagonseats == 0) {
            for (Train train : trains) {
                if (train.getName().equals(traint)) {
                    Wagon newWagon = new Wagon(wagont);
                    newWagon.setTrainName(train.getName());
                    train.addWagon(newWagon);
                    dbController.addObject(newWagon);
                }
            }
        } else {
            if (wagont != null && wagonseats != 0) {
                for (Train train : trains) {
                    if (train.getName().equals(traint)) {
                        Wagon newWagon = new Wagon(wagont, wagonseats);
                        newWagon.setTrainName(train.getName());
                        train.addWagon(newWagon);
                        dbController.addObject(newWagon);
                    }
                }
            }
        }

        trainChanged();

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
        int totalSeats = 0;
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
