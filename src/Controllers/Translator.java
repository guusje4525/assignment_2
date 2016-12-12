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

    public Translator(String commandInput){
        this.commandInput = commandInput.split(" ");

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

        Command commands = new Command("action", "commands");
        commands.addCommands(newCommand);
        commands.addCommands(getCommand);
        commands.addCommands(deleteCommand);

        getCommand(commands);

        System.out.println(c.toString());

    }

    void getCommand(Command commands){

        for(Command comm : commands.getCommands()){

            if((index >= 0) && (index < this.commandInput.length)){

                Boolean validAction = true;
                Boolean changed = false;
                if(debug) System.out.println("Running test, name: " + this.commandInput[index] + " - and comm.getName(): " + comm.getName());
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
