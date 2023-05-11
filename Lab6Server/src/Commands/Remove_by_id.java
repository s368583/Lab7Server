package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;


/**
 * Класс для удаления элемента из коллекции по его id
 * @author Arina Nikitochkina
 */

public class Remove_by_id extends Command {
    public Remove_by_id() {
        this.description = "удалить элемент из коллекции по его id";
        this.argument = true;
    }

    /**
     * Метод для выполнения команды Remove_by_id
     */

    @Override
    public void execute(Object arguments) {
        String string = "";
        try {
            long id = Long.parseLong(String.valueOf(arguments));
            boolean checkid = false;
            for(LabWork labWork : Lab5.collection)
                if(labWork.getId() == id) {
                    Lab5.collection.remove(labWork);
                    string = "\nЭлемент с id (" + id + ") был удалён";
                    checkid = true;
                }
            if (!checkid){
                string = "\nТакого id не существует";
            }
        } catch(Exception e) {
            string = ("\nДанные введены неверно\n");
        }
        if (Lab5.console){
            System.out.println(string);
        }
        else {Lab5.output = string;}

    }
}
