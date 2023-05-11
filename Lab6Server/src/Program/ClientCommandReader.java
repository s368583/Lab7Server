package Program;

import java.io.IOException;
import java.util.Objects;

public class ClientCommandReader {

    public ClientCommandReader() {}

    public void execute() {
        Lab5.script = false;
        Lab5.console = false;

        String clientLine = Lab5.lastString;
        if(!clientLine.isEmpty()) {
            if(clientLine.contains(" ")) {
                String[] clientIn = clientLine.split(" ");
                String command = clientIn[0].substring(0, 1).toUpperCase() + clientIn[0].substring(1);
                String argument = clientIn[1];

                Class<?> commandClass = null;
                try {
                    commandClass = Class.forName("Commands." + command);
                    Object commandClassObject = null;
                    try {
                        commandClassObject = commandClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((Command) Objects.requireNonNull(commandClassObject)).execute(argument);
                    Lab5.lastString = "";
                } catch (ClassNotFoundException e) {
                    Lab5.output = "\nКоманда не найдена\n";
                    Lab5.lastString = "";
                }
            }
            else {
                String command = clientLine.substring(0, 1).toUpperCase() + clientLine.substring(1);

                Class<?> commandClass = null;
                try {
                    commandClass = Class.forName("Commands." + command);
                    Object commandClassObject = null;
                    try {
                        commandClassObject = commandClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((Command) Objects.requireNonNull(commandClassObject)).execute();
                    Lab5.lastString = "";
                } catch (ClassNotFoundException e) {
                    Lab5.output = "\nКоманда не найдена\n";
                    Lab5.lastString = "";
                }
            }
        }
        else {
            try {
                Lab5.server.send("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Lab5.console = true;
    }
}
