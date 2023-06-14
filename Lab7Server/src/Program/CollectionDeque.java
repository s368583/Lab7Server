package Program;

import CollectionData.LabWork;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Класс для формирования очереди
 * @author Arina Nikitochkina
 */

public class CollectionDeque extends ConcurrentLinkedDeque<LabWork> {

    protected LocalDateTime creationDate;
    protected String type = this.getClass().getName();

    /**
     * Метод для получения информации о коллекции
     */
    public CollectionDeque() {
        creationDate = LocalDateTime.now();
        type = type.substring(type.lastIndexOf(".") + 1);
    }

    /**
     * Метод для вывода первого элемента коллекции
     */
    public String head() {
        return ("\n" + String.valueOf(peekFirst()));
    }

    /**
     * Метод для вывода информации о коллекции
     */
    public String info() {
        return ("\nИнформация о коллекции: " +
                "\n    Тип коллекции: " + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1) +
                "\n    Дата инициализации: " + creationDate +
                "\n    Количество элементов: " + size() + "\n");

    }

}
