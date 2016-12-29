package Controllers;

import Observer.Addtrain;
import Observer.Addwagon;
import Trains.Train;
import Trains.TrainStorage;
import Trains.Wagon;
import Ui.Gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guus on 12/12/2016.
 */
public class Controller {
    TrainStorage storage = new TrainStorage();
    Addtrain newtrain = new Addtrain(storage);
    Addwagon newwagon = new Addwagon(storage);
    Gui ui;

    public void start() {
        storage.loadTrains();
        ui = new Gui(this);
        ui.createFrame();
    }

    public void updateConsole(String newOutput) {
        ui.setConsoleOutput(newOutput);
        refreshScreen();
    }

    public void refreshScreen() {
        ui.refreshScreen();
    }

    public void clearConsole() {
        ui.clearConsole();
    }

    public void setItemsInSelectTrain() {
        ui.setItemsInSelectTrain();
    }

    public List<String> getTrainNames() {
        ArrayList<String> trainList = new ArrayList<>();
        for (Train tempTrain : getTrains()) {
            trainList.add(tempTrain.getName());
        }
        return trainList;
    }

    public List<String> getWagonNames(String trainName) {
        ArrayList<String> wagonList = new ArrayList<>();
        for (Train tempTrain : getTrains()) {
            if (tempTrain.getName().equals(trainName)) {
                for (Wagon wagon : tempTrain.getWagons()) {
                    wagonList.add(wagon.getWagon());
                }
            }
        }
        return wagonList;
    }

    public void addTrain(String trainName) {
        //if train doesn't exist
        if (!storage.trainExist(trainName)) {
            storage.updateTrains(trainName, null, 0);
            this.updateConsole("New train added");
        } else this.updateConsole("Train name must be unique");
    }

    public void addWagon(String trainName, String wagonName) {
        if (!storage.wagonExist(wagonName)) {
            storage.updateTrains(trainName, wagonName, 0);
            this.updateConsole("New wagon added");
        } else this.updateConsole("Wagon name must be unique");
    }

    public void addWagon(String trainName, String wagonName, int seats) {
        if (!storage.wagonExist(wagonName)) {
            storage.updateTrains(trainName, wagonName, seats);
            this.updateConsole("New wagon added");
        } else this.updateConsole("Wagon name must be unique");
    }

    public String getWagonSeats(String wagonName) {
        if (storage.getWagonSeats(wagonName) == -1) return "No wagon found";
        else return "" + storage.getWagonSeats(wagonName);
    }

    public String getTrainSeats(String trainName) {
        return storage.getTrainSeats(trainName);
    }

    public int getAllSeats() {
        return storage.getAllSeats();
    }

    public void deleteTrain(String trainName) {
        if (storage.deleteTrain(trainName)) updateConsole("Train was deleted");
        else updateConsole("Unable to find train");
    }

    public void deleteWagon(String wagonName) {
        if (storage.deleteWagon(wagonName)) updateConsole("Wagon was deleted");
        else updateConsole("Unable to find wagon");
    }

    public List<Train> getTrains() {
        return storage.getTrains();
    }

    public String createDSL() {
        String returnValue = "";
        for (Train t : getTrains()) {
            returnValue += "(" + t.getName() + ")";
            if (!t.getWagons().isEmpty()) {
                for (Wagon w : t.getWagons()) {
                    returnValue += "(" + w.getWagon() + ")";
                }
            }
            returnValue += "\n";
        }
        return returnValue;
    }

}
