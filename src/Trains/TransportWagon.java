package Trains;

/**
 * Created by Guus on 12/12/2016.
 */
public class TransportWagon extends Wagon{

    private String type;

    public TransportWagon(String name, int seats){
        super(name, seats);
        this.type = "Transport";
    }

}
