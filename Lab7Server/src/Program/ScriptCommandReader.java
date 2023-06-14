package Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Класс для чтения команд из скрипта
 * @author Arina Nikitochkina
 */
public class ScriptCommandReader {

    public ScriptCommandReader() {}

    /**
     * Метод для чтения команд из скрипта
     */
    public void start() {
        Lab5.script = true;
        File scriptFile = new File(Lab5.scriptPath);
        Scanner scriptScanner = null;

        try {
            scriptScanner = new Scanner(scriptFile);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        if(scriptScanner != null) {
            while(scriptScanner.hasNextLine()) {
                Lab5.scriptLines.add(scriptScanner.nextLine());
            }
        }

        while(!Lab5.scriptLines.isEmpty()) {
            if(Lab5.scriptLines.peek().contains(" ")) {
                String[] scriptIn = Lab5.scriptLines.poll().split(" ");
                String command = scriptIn[0].substring(0, 1).toUpperCase() + scriptIn[0].substring(1);
                String argument = scriptIn[1];

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
                } catch (ClassNotFoundException e) {
                    System.out.println("\nКоманда не найдена");
                }
            }
            else {
                String command = Lab5.scriptLines.poll();
                command = command.substring(0, 1).toUpperCase() + command.substring(1);

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
                } catch (ClassNotFoundException e) {
                    System.out.println("\nКоманда не найдена");
                }
            }
        }

        System.out.println();

        Lab5.scriptLines.clear();
        Lab5.script = false;
    }
}