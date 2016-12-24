package Controllers;

import java.util.ArrayList;

/**
 * Created by Guus on 12/12/2016.
 */
public class Translator {

    private String[] commandInput;
    private int index = 0;
    private Command command = new Command();
    private Boolean debug = false;
    private TempCommand commands;

    public Translator(String commandInput){
        this.commandInput = commandInput.split(" ");
        //Creating the schema for the commands
        createCommandSchema();

        //Translating commandInput to an command object
        getCommand(commands);

        command.execute();
    }

    void createCommandSchema(){
        //NEW command
        //.setCommandToExecute("TrainStorage.addTrain(:train-name:)")
        TempCommand newCommand =
                new TempCommand("action", "new",
                        new TempCommand("action", "train",
                                new TempCommand("string", "train-name")
                        ),
                        new TempCommand("action", "wagon",
                                new TempCommand("string", "wagon-name",
                                        new TempCommand("action", "seats",
                                                new TempCommand("integer", "number-seats",
                                                        new TempCommand("action", "train",
                                                                new TempCommand("string", "train-name")
                                                        )
                                                )
                                        ),
                                        new TempCommand("action", "train",
                                                new TempCommand("string", "train-name")
                                        )
                                )
                        )
                );

        //GET command
        TempCommand getCommand =
                new TempCommand("action", "get",
                        new TempCommand("action", "seats",
                                new TempCommand("action", "train",
                                        new TempCommand("string", "train-name")
                                ),
                                new TempCommand("action", "wagon",
                                        new TempCommand("string", "wagon-name")
                                ),
                                new TempCommand("action", "all")
                        )
                );

        //DELETE command
        TempCommand deleteCommand =
                new TempCommand("action", "delete",
                        new TempCommand("action", "train",
                                new TempCommand("string", "train-name")
                        ),
                        new TempCommand("action", "wagon",
                                new TempCommand("string", "wagon-name")
                        )
                );

        commands = new TempCommand("action", "commands");
        commands.addCommands(newCommand);
        commands.addCommands(getCommand);
        commands.addCommands(deleteCommand);
        commands.addCommands(new TempCommand("action", "help")); //HELP command
    }

    void getCommand(TempCommand commands){

        for(TempCommand comm : commands.getCommands()){

            //Checking if the commandInput is not running out of bounds
            if((index >= 0) && (index < this.commandInput.length)){

                Boolean validAction = true;
                Boolean changed = false;
                if(debug) System.out.println("Running test, name: " + this.commandInput[index] + " - and comm.getName(): " + comm.getName());

                //Checking what actiontype the current command we are loping though has
                switch(comm.getActionType()){
                    case "action" :
                        if(index == 0 && this.commandInput[index].equals(comm.getName())) command.setAction(this.commandInput[index]);
                        if(this.commandInput[index].equals(comm.getName())) changed = true;
                        break;
                    case "string" :
                        command.addStringArgs(comm.getName(), this.commandInput[index]);
                        changed = true;
                        break;
                    case "integer" :
                        command.addIntegerArgs(comm.getName(), Integer.parseInt(this.commandInput[index]));
                        changed = true;
                        break;
                    default :
                        validAction = false;
                        break;
                }

                //Some checks to make sure the commandInput is valid
                if(validAction){
                    if(comm.getCommands() == null) {
                        return;
                    } else if(changed){
                        if(debug) System.out.println("Increasing index...");
                        index++;
                        getCommand(comm);
                    }
                }
            }
        }
    }

    class TempCommand {

        private String ActionType;
        private String name;
        private ArrayList<TempCommand> commands = null;
        private String CommandToExecute;

        public TempCommand(String ActionType, String name){
            this.ActionType = ActionType;
            this.name = name;
        }

        //Currently not being used, but I like to keep the options open in case I want to use it later
        public TempCommand(String ActionType, String name, ArrayList<TempCommand> command){
            this.ActionType = ActionType;
            this.name = name;
            this.commands = command;
        }

        public TempCommand(String ActionType, String name, TempCommand command){
            this.ActionType = ActionType;
            this.name = name;
            this.commands = new ArrayList<>();
            this.commands.add(command);
        }

        public TempCommand(String ActionType, String name, TempCommand command, TempCommand command2){
            this.ActionType = ActionType;
            this.name = name;
            this.commands = new ArrayList<>();
            this.commands.add(command);
            this.commands.add(command2);
        }

        public TempCommand(String ActionType, String name, TempCommand command, TempCommand command2, TempCommand command3){
            this.ActionType = ActionType;
            this.name = name;
            this.commands = new ArrayList<>();
            this.commands.add(command);
            this.commands.add(command2);
            this.commands.add(command3);
        }

        public void addCommands(TempCommand command){
            if(commands == null) commands = new ArrayList<>();
            this.commands.add(command);
        }

        public ArrayList<TempCommand> getCommands(){
            return commands;
        }

        public String getName(){
            return name;
        }

        public String getActionType(){
            return ActionType;
        }

        public String getCommandToExecute(){
            return CommandToExecute;
        }

        public void setCommandToExecute(String comm) {
            CommandToExecute = comm;
        }

    }

}
