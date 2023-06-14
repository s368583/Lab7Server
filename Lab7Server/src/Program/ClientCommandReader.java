package Program;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ClientCommandReader extends Thread implements Runnable {

    public Exchanger<String> exchanger;
    public String username = "";
    public String password = "";

    public ClientCommandReader(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Lab5.script = false;
        Lab5.console = false;

        String clientLine = Lab5.lastString;
        System.out.println(Lab5.lastString);
        if(!clientLine.isEmpty()) {
            if(clientLine.contains(" ")) {
                String[] clientIn = clientLine.split(" ");
                String command = "";
                String argument = "";
                if(clientIn[0].contains("::")) {
                    String[] clientLCIn = clientIn[0].split("::");
                    username = clientLCIn[0];
                    password = clientLCIn[1];
                    command = clientLCIn[2].substring(0, 1).toUpperCase() + clientLCIn[2].substring(1);
                    argument = clientIn[1];
                }
                else {
                    command = clientIn[0].substring(0, 1).toUpperCase() + clientIn[0].substring(1);
                    argument = clientIn[1];
                }
                /*System.out.println(command);
                System.out.println(argument);
                System.out.println(username);
                System.out.println(password);*/

                if(!username.isEmpty()) {
                    try {
                        if(SQLManager.accessed(username, password)) { //пользователь существует и верный пароль
                            if(command.equals("Remove_by_id") || command.equals("Update")) {
                                String arg = argument;
                                if (command.equals("Update")){
                                    arg = arg.split("%%%")[0];
                                }
                                if(SQLManager.editable(username, password, Integer.parseInt(arg))) { //создал username элемент с id argument
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
                                    Lab5.output = "\nУ вас нет прав на редактирование/удаление данного объекта\n";
                                    Lab5.lastString = "";
                                }
                            }
                            else {
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
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
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
            }
            else {
                String command = "";
                if(clientLine.contains("::")) {
                    String[] clientLCIn = clientLine.split("::");
                    username = clientLCIn[0];
                    password = clientLCIn[1];
                    command = clientLCIn[2].substring(0, 1).toUpperCase() + clientLCIn[2].substring(1);
                }
                else {
                    command = clientLine.substring(0, 1).toUpperCase() + clientLine.substring(1);
                }

                if(!username.isEmpty()) {
                    try {
                        if(SQLManager.accessed(username, password)) {
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
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
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
        }
        else {
            try {
                Lab5.server.send("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            //exchanger - поток сервера, Lab5.output - поток команды, эти значения меняются
            exchanger.exchange(Lab5.output);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Lab5.console = true;
    }
}
