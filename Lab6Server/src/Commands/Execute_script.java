package Commands;

import Program.Command;
import Program.Lab5;
import Program.ScriptCommandReader;

/**
 * Класс для считывания и исполнения скрипта из файла
 * @author Arina Nikitochkina
 */

public class Execute_script extends Command {

    public Execute_script() {
        this.description = "Считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
        this.argument = true;
    }

    /**
     * Метод для выполнения команды Execute_script
     */

    @Override
    public void execute(Object filepath) {
        Lab5.scriptPath = System.getProperty("user.dir")+"/Lab6Server/src/" + filepath;
        try {
            new ScriptCommandReader().start();
        } catch (Exception e) {
            System.out.println("Неверный формат скрипта");

        }
    }
}