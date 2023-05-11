package Program;

import com.google.gson.Gson;

/**
 * Абстрактый класс для комманд
 * Здесь прописаны методы, которые будут переопределены для каждой команды
 * @author Arina Nikitochkina
 */
public abstract class Command {

    public Gson gson = new Gson();
    protected String description;
    protected boolean argument = false;

    /**
     * Метод для описания команды
     */
    public Command() {
        this.description = "";
    }

    /**
     * Метод для выполнения команды (когда нет входных аргументов)
     */
    public void execute() {}
    /**
     * Метод для выполнения команды (когда есть входные аргументы)
     */
    public void execute(Object arguments) {}

    /**
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArgument() {
        return argument;
    }
}
