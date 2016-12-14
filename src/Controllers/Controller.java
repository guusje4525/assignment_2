package Controllers;

import Trains.Train;
import Trains.TrainStorage;
import Trains.Wagon;
import Ui.Gui;

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
        Gui.refreshDSL();
    }

    public static void addTrain(String trainName){
        TrainStorage.addTrain(trainName);
        Controller.updateConsole("New train added");
    }

    public static void addWagon(String trainName, String wagonName){
        TrainStorage.addWagon(trainName, wagonName);
        Controller.updateConsole("New wagon added");
    }

    public static void addWagon(String trainName, String wagonName, int seats){
        TrainStorage.addWagon(trainName, wagonName, seats);
        Controller.updateConsole("New wagon added");
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

    public static String deleteTrain(String trainName){
        if(TrainStorage.deleteTrain(trainName)) return "Train was deleted";
        return "Unable to find train";
    }

    public static String deleteWagon(String wagonName){
        if(TrainStorage.deleteWagon(wagonName)) return "Wagon was deleted";
        return "Unable to find wagon";
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
