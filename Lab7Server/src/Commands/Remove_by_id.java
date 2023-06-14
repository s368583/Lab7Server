package Commands;

import CollectionData.LabWork;
import Program.*;

import java.sql.SQLException;


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
    public void execute(Object id) {
        try {
            if(SQLManager.removeLabWork(Integer.parseInt(String.valueOf(id)))) { //если есть, что удалить и удаляет, то Т

                if(Lab5.console)
                    System.out.println("\nЭлемент с id (" + id + ") был удалён");
                else
                    Lab5.output = "\nЭлемент с id (" + id + ") был удалён";
            }
            else {
                if(Lab5.console)
                    System.out.println("\nТакого id не существует");
                else
                    Lab5.output = "\nТакого id не существует";
            }
            SQLManager.fill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
