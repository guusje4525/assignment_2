package Controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Guus on 12/12/2016.
 */
public class Command {

    String action = "";
    Map<String, String> StringArgs = new HashMap<>();
    Map<String, Integer> IntegerArgs = new HashMap<>();

    public Command(){}
    void setAction(String action){
        this.action = action;
    }
    void addStringArgs(String key, String value){
        StringArgs.put(key, value);
    }
    void addIntegerArgs(String key, int value){
        IntegerArgs.put(key, value);
    }
    public String toString(){
        String returnValue = "Action: " + action + "\n";
        Iterator it = StringArgs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            returnValue += pair.getKey() + ": " + pair.getValue() + "\n";
            it.remove();
        }

        Iterator it2 = IntegerArgs.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();
            returnValue += pair.getKey() + ": " + pair.getValue() + "\n";
            it2.remove();
        }

        return returnValue;
    }
    public void execute(){
        switch(action){
            case "new":
                if(StringArgs.containsKey("train-name")){
                    if(StringArgs.containsKey("wagon-name")){
                        if(IntegerArgs.containsKey("number-seats")) Controller.addWagon(StringArgs.get("train-name"), StringArgs.get("wagon-name"), IntegerArgs.get("number-seats"));
                        else Controller.addWagon(StringArgs.get("train-name"), StringArgs.get("wagon-name"));
                    } else Controller.addTrain(StringArgs.get("train-name"));
                } else Controller.updateConsole("Error occurred while trying to create a new train");
                break;
            case "get":
                if(StringArgs.containsKey("train-name")) Controller.updateConsole(StringArgs.get("train-name") + ": " + Controller.getTrainSeats(StringArgs.get("train-name")) + " seats");
                else if(StringArgs.containsKey("wagon-name")) Controller.updateConsole(StringArgs.get("wagon-name") + ": " + Controller.getWagonSeats(StringArgs.get("wagon-name")) + " seats");
                else Controller.updateConsole("All seats: " + Controller.getAllSeats());
                break;
            case "delete":
                if(StringArgs.containsKey("train-name")) Controller.updateConsole(Controller.deleteTrain(StringArgs.get("train-name")));
                else if(StringArgs.containsKey("wagon-name")) Controller.updateConsole(Controller.deleteWagon(StringArgs.get("wagon-name")));
                else Controller.updateConsole("Unknow command");
                break;
            case "help":
                Controller.updateConsole("There is no help available right now. Please try again later");
                break;
            default:
                Controller.updateConsole("Unknown command");
                break;
        }
    }
}
