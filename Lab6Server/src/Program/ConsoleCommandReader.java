package Program;

import java.util.Objects;
import java.util.Scanner;

/**
 * Класс для чтения команд из консоли
 * @author Arina Nikitochkina
 */
public class ConsoleCommandReader {
    public ConsoleCommandReader() {}

    /**
     * Метод для чтения команд из консоли
     * @throws Exception
     */
    public static void start() throws Exception {
        Lab5.script = false;
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Для просмотра доступных команд введите help");
            System.out.println("Введите команду:");

            String consoleLine = scanner.nextLine();

            if(consoleLine.isEmpty()) {
                continue;
            }
            if(consoleLine.contains(" ")) {
                String[] consoleInput = consoleLine.split(" ");
                String commandLine = consoleInput[0].substring(0, 1).toUpperCase() + consoleInput[0].substring(1);
                String argumentLine = null;
                try{ argumentLine = consoleInput[1];}
                catch (Exception e) {
                    System.out.println("Вы не ввели аргумент");
                    continue;

                }
                try {
                    Class commandClass = Class.forName("Commands." + commandLine);
                    Command commandClassObject = (Command) commandClass.newInstance();
                    if (((Command) Objects.requireNonNull(commandClassObject)).isArgument()){
                        commandClassObject.execute(argumentLine);}
                } catch (ClassNotFoundException e) {
                    System.out.println("Команда не найдена");
                }
            }
            else {
                String commandLine = consoleLine.substring(0, 1).toUpperCase() + consoleLine.substring(1);

                try {
                    Class commandClass = Class.forName("Commands." + commandLine);
                    Command commandClassObject = (Command) commandClass.newInstance();
                    if (!((Command) Objects.requireNonNull(commandClassObject)).isArgument()){
                        commandClassObject.execute();}
                    else{
                        System.out.println("Вы не ввели аргумент");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Команда не найдена");
                }
            }
        }
    }
}
