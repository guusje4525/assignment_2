package Service;

import Controllers.Controller;
/**
 * Created by Guus on 12/12/2016.
 */
public class TrainService {

    public static void main(String[] args){

        Controller.start();

        Controller.clearConsole();
        Controller.setItemsInSelectTrain(); //This function should be deprecated when Dao is correctly implemented

    }

}
