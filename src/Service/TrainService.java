package Service;

import Controllers.Controller;

/**
 * Created by Guus on 12/12/2016.
 */
public class TrainService {

    public static void main(String[] args){

        Controller.addTrain("trein1");
        Controller.addTrain("trein2");
        Controller.addTrain("trein3");

        Controller.addWagon("trein1", "wagon1", 50);
        Controller.addWagon("trein1", "wagon2", 30);
        Controller.addWagon("trein2", "wagon3", 10);
        Controller.addWagon("trein2", "wagon4");
        Controller.addWagon("trein3", "wagon5");
        Controller.addWagon("trein3", "wagon6");
        Controller.addWagon("trein3", "wagon7");
        Controller.addWagon("trein5", "wagon70");
        Controller.addWagon("trein78", "wagon70");

        Controller.start();

    }

}
