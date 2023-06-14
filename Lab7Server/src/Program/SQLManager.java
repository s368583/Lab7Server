package Program;

import CollectionData.Coordinates;
import CollectionData.Difficulty;
import CollectionData.Discipline;
import CollectionData.LabWork;

import java.sql.*;

public class SQLManager {

    private static final String url = "jdbc:postgresql://localhost:5432/studs"; //адресс бд
    private static final String user = "s368583";
    private static final String password = "GHUcw9I0LX3OMbqA";

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public SQLManager() {}

    public void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Successfully connected to " + url);
    }


    public static synchronized boolean userExists(String username) throws SQLException { //сущ ли пользователь в базе
        preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        preparedStatement.setString(1, username); //меняет ? на username
        resultSet = preparedStatement.executeQuery(); //результат выполнения команды для sql
        return resultSet.next();  //true, если что-то пполучил из бд (значит, что такой username есть)
    }

    public static synchronized void addUser(String username, String password) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?,?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.execute();
    }

    public static synchronized boolean accessed(String username, String password) throws SQLException { //совпадение логина и пароля
        if (userExists(username)) {
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery(); //получаем ответ от бд
            return resultSet.next();
        }
        return false;
    }

    public static synchronized boolean editable(String username, String password, int id) throws SQLException { //может ли польз изменять объект
        if (accessed(username, password)) {
            preparedStatement = connection.prepareStatement("SELECT * FROM labworks WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return username.equals(resultSet.getString(13));
        }
        return false;
    }


    public static synchronized void addLabWork(LabWork labWork) { //добавляет элемент в бд
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO labworks " +
                    "(name, coord_x, coord_y, minimal_point, maximum_point, average_point, difficulty, discipline_name, lecture_hours, selfstudy_hours, labs_count, username)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, labWork.getName());
            preparedStatement.setFloat(2, labWork.getCoordinates().getX());
            preparedStatement.setFloat(3, labWork.getCoordinates().getY());
            preparedStatement.setDouble(4, labWork.getMinimalPoint());
            preparedStatement.setDouble(5, labWork.getMaximumPoint());
            preparedStatement.setDouble(6, labWork.getAveragePoint());
            preparedStatement.setString(7, labWork.getDifficulty().name());
            preparedStatement.setString(8, labWork.getDiscipline().getName());
            preparedStatement.setInt(9, labWork.getDiscipline().getLectureHours());
            preparedStatement.setInt(10, labWork.getDiscipline().getSelfStudyHours());
            preparedStatement.setInt(11, labWork.getDiscipline().getLabsCount());
            preparedStatement.setString(12, labWork.getUser());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean removeLabWork(int id) throws SQLException { //удаляет эл-т из бд
        preparedStatement = connection.prepareStatement("SELECT * FROM labworks WHERE id = ?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement = connection.prepareStatement("DELETE FROM labworks WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        }
        return false;
    }

    public static synchronized void updateLabWork(int id, LabWork labWork) throws SQLException { //обновляет эл-т по id
        preparedStatement = connection.prepareStatement("UPDATE labworks SET name = ?, coord_x = ?, coord_y = ?," +
                "minimal_point = ?, maximum_point = ?, average_point = ?, difficulty = ?, discipline_name = ?," +
                "lecture_hours = ?, selfstudy_hours = ?, labs_count = ?, username = ? WHERE id = ?");

        preparedStatement.setString(1, labWork.getName());
        preparedStatement.setFloat(2, labWork.getCoordinates().getX());
        preparedStatement.setFloat(3, labWork.getCoordinates().getY());
        preparedStatement.setDouble(4, labWork.getMinimalPoint());
        preparedStatement.setDouble(5, labWork.getMaximumPoint());
        preparedStatement.setDouble(6, labWork.getAveragePoint());
        preparedStatement.setString(7, labWork.getDifficulty().name());
        preparedStatement.setString(8, labWork.getDiscipline().getName());
        preparedStatement.setInt(9, labWork.getDiscipline().getLectureHours());
        preparedStatement.setInt(10, labWork.getDiscipline().getSelfStudyHours());
        preparedStatement.setInt(11, labWork.getDiscipline().getLabsCount());
        preparedStatement.setString(12, labWork.getUser());

        preparedStatement.setInt(13, id);
        preparedStatement.execute();
    }


    public static synchronized void clear() throws SQLException {
        statement = connection.createStatement();
        statement.execute("TRUNCATE labworks");
    }

    public static synchronized void fill() throws SQLException { //берет все эл-ты из бд и загружает их в код
        Lab5.collection.clear();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM labworks");

        while (resultSet.next()) {
            Coordinates coordinates = new Coordinates(resultSet.getInt(3), resultSet.getInt(4));

            Discipline discipline = new Discipline(resultSet.getString(9), resultSet.getInt(10),
                    resultSet.getInt(11), resultSet.getInt(12));

            LabWork labWork = new LabWork(resultSet.getString(2), coordinates, resultSet.getInt(5),
                    resultSet.getDouble(6), resultSet.getDouble(7),
                    Difficulty.valueOf(resultSet.getString(8)), discipline);

            labWork.setId((long) resultSet.getInt(1));
            labWork.setUser(resultSet.getString(13));

            Lab5.collection.add(labWork);
        }
    }
}