package Controllers;

import java.util.*;

/**
 * Created by Guus on 12/12/2016.
 */
public class Translator {

    private String[] commandInput;
    private String consoleOutput = "Command is not valid";
    private int index = 0;
    private TempCommand c = new TempCommand();
    private Boolean debug = true;
    private Command commands;

    public Translator(String commandInput){
        this.commandInput = commandInput.split(" ");
        //Creating the schema for the commands
        createCommandSchema();

        //Translating commandInput to an command object
        getCommand(commands);

        System.out.println(c.toString());

    }

    void createCommandSchema(){
        //NEW command
        Command newCommand =
                new Command("action", "new",
                        new Command("action", "train",
                                new Command("string", "train-name")
                        ),
                        new Command("action", "wagon",
                                new Command("string", "wagon-name",
                                        new Command("action", "seats",
                                                new Command("integer", "number-seats",
                                                        new Command("action", "train",
                                                                new Command("string", "train-name")
                                                        )
                                                )
                                        ),
                                        new Command("action", "train",
                                                new Command("string", "train-name")
                                        )
                                )
                        )
                );

        //GET command
        Command getCommand =
                new Command("action", "get",
                        new Command("action", "seats",
                                new Command("action", "train",
                                        new Command("string", "train-name")
                                ),
                                new Command("action", "wagon",
                                        new Command("string", "wagon-name")
                                )
                        )
                );

        //DELETE command
        Command deleteCommand =
                new Command("action", "delete",
                        new Command("action", "train",
                                new Command("string", "train-name")
                        ),
                        new Command("action", "wagon",
                                new Command("string", "wagon-name")
                        )
                );

        commands = new Command("action", "commands");
        commands.addCommands(newCommand);
        commands.addCommands(getCommand);
        commands.addCommands(deleteCommand);
    }

    void getCommand(Command commands){

        for(Command comm : commands.getCommands()){

            //Checking if the commandInput is not running out of bounds
            if((index >= 0) && (index < this.commandInput.length)){

                Boolean validAction = true;
                Boolean changed = false;
                if(debug) System.out.println("Running test, name: " + this.commandInput[index] + " - and comm.getName(): " + comm.getName());

                //Checking what actiontype the current command we are loping though has
                switch(comm.getActionType()){
                    case "action" :
                        if(index == 0 && this.commandInput[index].equals(comm.getName())) c.setAction(this.commandInput[index]);
                        if(this.commandInput[index].equals(comm.getName())) changed = true;
                        break;
                    case "string" :
                        c.addStringArgs(comm.getName(), this.commandInput[index]);
                        changed = true;
                        break;
                    case "integer" :
                        c.addIntegerArgs(comm.getName(), Integer.parseInt(this.commandInput[index]));
                        changed = true;
                        break;
                    default :
                        validAction = false;
                        break;
                }

                //Some checks to make sure the commandInput is valid
                if(validAction){
                    if(comm.getCommands() == null) {
                        consoleOutput = "Command is valid";
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

        String action = "";
        Map<String, String> StringArgs = new HashMap<>();
        Map<String, Integer> IntegerArgs = new HashMap<>();

        public TempCommand(){}
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

    }

    public String toString(){
        return consoleOutput;
    }

}
