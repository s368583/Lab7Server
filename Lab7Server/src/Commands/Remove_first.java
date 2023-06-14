package Commands;

import Program.Command;
import Program.Lab5;
import Program.SQLManager;

import java.sql.SQLException;

/**
 * Класс для удаления первого элемента из коллекции
 * @author Arina Nikitochkina
 */

public class Remove_first extends Command {
    public Remove_first() {
        this.description = "удалить первый элемент из коллекции";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Remove_first
     */

    @Override
    public void execute() {
        long first_id = Lab5.collection.peekFirst().getId();
        if(Lab5.console){
            new Remove_by_id().execute(first_id);
            try {
                SQLManager.fill();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\nПервый элемент удален");
        }
        else {

            String l = Lab5.lastString.substring(0, Lab5.lastString.lastIndexOf("remove_first")).split("::")[0];
            String p = Lab5.lastString.substring(0, Lab5.lastString.lastIndexOf("remove_first")).split("::")[1];
            try {
                if (SQLManager.editable(l, p, (int) first_id)) {
                    new Remove_by_id().execute(first_id);
                    try {
                        SQLManager.fill();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else Lab5.output = "\nУ вас нет прав на удаление данного объекта";
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}