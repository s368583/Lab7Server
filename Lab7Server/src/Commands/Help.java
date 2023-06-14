package Commands;

import Program.Command;
import Program.Lab5;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.util.ArrayList;

/**
 * Класс для вывода справки по доступным командам
 * @author Arina Nikitochkina
 */

public class Help extends Command {
    public Help() {
        this.description = "вывести справку по доступным командам";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Help
     */

    @Override
    public void execute() {
        ArrayList<String[]> help_out = new ArrayList<>();
        File directory = new File(System.getProperty("user.dir")+"/Lab7Server/src/Commands"); //ссылка папку, в которой хранятся все команды
        String[] commandClasses = directory.list(); //массив из названий всех элементов в папке


        for(String filename : commandClasses) {
            if(filename.endsWith(".java")) {
                String[] com_desc = new String[2];
                com_desc[0] = filename.replace(".java", "").toLowerCase(); //название команды
                try {
                    Class cls = Class.forName("Commands." + filename.replace(".java", ""));
                    Command commandClassObject = (Command) cls.newInstance();
                    com_desc[1] = commandClassObject.getDescription();
                }
                catch (Exception ignored) {}

                help_out.add(com_desc);
            }
        }

        String output = "";
        output += "Доступные команды:\n";
        for(String[] comhelp : help_out) {
            output += "   " + comhelp[0] + " : " + comhelp[1] + "\n";
        }
        System.out.println(output);
    }
}
