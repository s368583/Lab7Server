package Program;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс для выполнения команд, записи коллекции, чтения скрипта
 * Главный класс
 * @author Arina Nikitochkina
 */


    public class Lab5 {

    public static Server server;
    public static Thread consoleCommandReader;
    public static String lastString = "";
    public static volatile String output = "";

    public static CollectionDeque collection = new CollectionDeque();


    public static void setCollection(CollectionDeque collection) {
        Lab5.collection = collection;
    }


    /** Имя файла скрипта     */
    public static String scriptPath = "";

    /** Очередь из команд     */
    public static Queue<String> scriptLines = new LinkedList<String>() {
    };

    /** script для того, чтобы при чтении скрипта он не перескакивал на консоль и до конца прочитал и понял скрипт   */
    public static boolean script = false;
    public static boolean console = true;

    public static void main(String[] args) throws Exception {
        server = new Server("localhost", 8989);
        server.start();
        new CollectionFiller().execute();
    }
}