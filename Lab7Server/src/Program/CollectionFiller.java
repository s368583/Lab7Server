package Program;


import CollectionData.LabWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Класс для файла и заполнения его данных в коллекцию
 * @author Arina Nikitochkina
 */
public class CollectionFiller extends Command {
    //protected Scanner scanner = new Scanner(new File("src/save.json"));
    //protected Gson gson = new Gson();


    /**
     * Метод для учета возможной ошибки по ходу чтения файла
     * @throws FileNotFoundException
     */
    public CollectionFiller() throws FileNotFoundException {}

    /**
     * Метод для заполнения коллекции из файла
     */
    @Override
    public void execute() {
        try {
            SQLManager.fill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
