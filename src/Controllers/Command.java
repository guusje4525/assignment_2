package Controllers;

import java.util.ArrayList;

/**
 * Created by Guus on 12/12/2016.
 */
public class Command {

    private String ActionType;
    private String name;
    private ArrayList<Command> commands = null;

    public Command(String ActionType, String name){
        this.ActionType = ActionType;
        this.name = name;
    }

    //Currently not being used, but I like to keep the options open in case I want to use it later
    public Command(String ActionType, String name, ArrayList<Command> command){
        this.ActionType = ActionType;
        this.name = name;
        this.commands = command;
    }

    public Command(String ActionType, String name, Command command){
        this.ActionType = ActionType;
        this.name = name;
        this.commands = new ArrayList<>();
        this.commands.add(command);
    }

    public Command(String ActionType, String name, Command command, Command command2){
        this.ActionType = ActionType;
        this.name = name;
        this.commands = new ArrayList<>();
        this.commands.add(command);
        this.commands.add(command2);
    }

    public void addCommands(Command command){
        if(commands == null) commands = new ArrayList<>();
        this.commands.add(command);
    }

    public ArrayList<Command> getCommands(){
        return commands;
    }

    public String getName(){
        return name;
    }

    public String getActionType(){
        return ActionType;
    }


}
