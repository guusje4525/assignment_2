package Controllers;

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

    public static void start(){
        Gui.createFrame();
        //observer hier voor events
    }

    public static void updateConsole(String newOutput){
        Gui.setConsoleOutput(newOutput);
        refreshScreen();
    }

    public static void refreshScreen() {
        Gui.refreshScreen();
    }

    public static void clearConsole() {
        Gui.clearConsole();
    }

    public static void setItemsInSelectTrain() {
        Gui.setItemsInSelectTrain();
    }

    public static List<String> getTrainNames() {
        ArrayList<String> trainList = new ArrayList<>();
        for (Train tempTrain : getTrains()) {
            trainList.add(tempTrain.getName());
        }
        return trainList;
    }

    public static List<String> getWagonNames(String trainName) {
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

    public static void addTrain(String trainName){
        //if train doesn't exist
        if (!TrainStorage.trainExist(trainName)) {
            TrainStorage.addTrain(trainName);
            Controller.updateConsole("New train added");
        } else Controller.updateConsole("Train name must be unique");
    }

    public static void addWagon(String trainName, String wagonName){
        if (!TrainStorage.wagonExist(wagonName)) {
            TrainStorage.addWagon(trainName, wagonName);
            Controller.updateConsole("New wagon added");
        } else Controller.updateConsole("Wagon name must be unique");
    }

    public static void addWagon(String trainName, String wagonName, int seats){
        if (!TrainStorage.wagonExist(wagonName)) {
            TrainStorage.addWagon(trainName, wagonName, seats);
            Controller.updateConsole("New wagon added");
        } else Controller.updateConsole("Wagon name must be unique");
    }

    public static String getWagonSeats(String wagonName){
        if(TrainStorage.getWagonSeats(wagonName) == -1) return "No wagon found";
        else return "" + TrainStorage.getWagonSeats(wagonName);
    }

    public static String getTrainSeats(String trainName){
        return TrainStorage.getTrainSeats(trainName);
    }

    public static int getAllSeats(){
        return TrainStorage.getAllSeats();
    }

    public static void deleteTrain(String trainName) {
        if (TrainStorage.deleteTrain(trainName)) updateConsole("Train was deleted");
        else updateConsole("Unable to find train");
    }

    public static void deleteWagon(String wagonName) {
        if (TrainStorage.deleteWagon(wagonName)) updateConsole("Wagon was deleted");
        else updateConsole("Unable to find wagon");
    }

    public static List<Train> getTrains() {
        return TrainStorage.getTrains();
    }

    public static String createDSL() {
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
