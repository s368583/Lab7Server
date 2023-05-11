package CollectionData;

import Program.Lab5;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Класс для описания и создания элементов коллекции
 * @author Arina Nikitochkina
 */

public class LabWork implements Comparable<LabWork> {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private double minimalPoint;
    private double maximumPoint;
    private double averagePoint;
    private Difficulty difficulty;
    private Discipline discipline;

    public LabWork(){};

    public LabWork(String name, Coordinates coordinates, double minimalPoint, double maximumPoint,
                   double averagePoint, Difficulty difficulty, Discipline discipline) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.maximumPoint = maximumPoint;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
        this.id = generateId();
    }

    /**
     * Метод для заполнения информации об элементе коллекции из скрипта или вручную пользователем с консоли
     * @return информация об элементе коллекции
     */
    public static LabWork create() {
        LabWork labWork = null;

        if(Lab5.script) {
            String name = Lab5.scriptLines.poll();
            Coordinates coordinates = Coordinates.create();
            double minimalPoint = Double.parseDouble(Lab5.scriptLines.poll());
            int maximumPoint = Integer.parseInt(Lab5.scriptLines.poll());
            int averagePoint = Integer.parseInt(Lab5.scriptLines.poll());
            Difficulty difficulty = null;
            try {
                difficulty = Difficulty.valueOf(Lab5.scriptLines.poll().toUpperCase());
            } catch (Exception ignored) {}
            Discipline discipline = Discipline.create();

            labWork = new LabWork(name, coordinates, minimalPoint, maximumPoint, averagePoint, difficulty, discipline);        }
        else {
            Scanner scanner = new Scanner(System.in);

            boolean check = false; //правильно ли введено с консоли

            System.out.print("Введите название работы: ");
            String name = null;
            while(!check) {
                try {
                    name = scanner.nextLine();
                    if(name != null && !name.equals(""))
                        check = true;
                    else {
                        System.out.println("\nДанные введены неверно");
                        System.out.print("Введите название работы: ");
                    }
                } catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите название работы: ");
                }
            }

            Coordinates coordinates = Coordinates.create();

            scanner = new Scanner(System.in);
            check = false;
            System.out.print("Введите минимальное количество баллов: ");
            Double minimalPoint = null;
            while(!check) {
                try {
                    minimalPoint = Double.parseDouble(scanner.nextLine());
                    check = true;
                } catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите минимальное количество баллов: ");
                }
            }

            scanner = new Scanner(System.in);
            check = false;
            System.out.print("Введите максимальное количество баллов: ");
            Double maximumPoint = 0.0;
            Double maybemax = null;
            while(!check) {
                try {
                    maybemax = Double.parseDouble(scanner.nextLine());
                    if (maybemax > minimalPoint){
                        maximumPoint = maybemax;
                        check = true;
                    }
                    else{
                        System.out.println("\nДанные введены неверно - мксимальный балл меньше/равен минимальному баллу");
                        System.out.print("Введите максимальное количество баллов: ");
                    }

                } catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите максимальное количество баллов: ");
                }
            }

            check = false;
            System.out.print("Введите средний балл: ");
            Double averagePoint = 0.0;
            Double maybeaverage = null;
            while(!check) {
                try {
                    maybeaverage = Double.parseDouble(scanner.nextLine());
                    if (maybeaverage < maximumPoint &&  maybeaverage > minimalPoint){
                        averagePoint = maybeaverage;
                        check = true;
                    }
                    else{
                        System.out.println("\nДанные введены неверно - средний балл находится вне промежутка максимального и манимального балла");
                        System.out.print("Введите максимальное количество баллов: ");
                    }

                }
                catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите средний балл: ");
                }
            }

            scanner = new Scanner(System.in);
            check = false;
            System.out.print("Введите сложность " + Arrays.toString(Difficulty.values()) + ": ");
            Difficulty difficulty = null;
            while(!check) {
                try {
                    difficulty = Difficulty.valueOf(scanner.nextLine().toUpperCase());
                    check = true;
                } catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите сложность " + Arrays.toString(Difficulty.values()) + ": ");
                }
            }

            Discipline discipline = Discipline.create();

            labWork =  new LabWork(name, coordinates, minimalPoint, maximumPoint, averagePoint, difficulty, discipline);
        }
        return labWork;
    }

    /**
     * Метод, для генерации id элемента коллекции
     * @return id элемента коллекции
     */

    public static long generateId() {
        int count = 0;
        long id = 1;
        while(count != Lab5.collection.size()) {
            for(LabWork labWork : Lab5.collection) {
                count++;
                if(labWork.getId() == id) {
                    id++;
                    count = 0;
                    break;
                }
            }
        }
        return id;
    }

    public Long getId() {
        return id;
    }
    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    @XmlElement(name = "coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public double getMinimalPoint() {
        return minimalPoint;
    }
    @XmlElement
    public void setMinimalPoint(double minimalPoint) {
        this.minimalPoint = minimalPoint;
    }
    public double getMaximumPoint() {
        return maximumPoint;
    }
    @XmlElement
    public void setMaximumPoint(double maximumPoint) {
        this.maximumPoint = maximumPoint;
    }
    public double getAveragePoint() {
        return averagePoint;
    }
    @XmlElement
    public void setAveragePoint(double averagePoint) {
        this.averagePoint = averagePoint;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    @XmlElement
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public Discipline getDiscipline() {
        return discipline;
    }
    @XmlElement
    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    /**
     * Класс-компаратор
     */

    public static class DisciplineComparator implements Comparator<LabWork> {
        /**
         * Метод для сравнения элементов коллекции по полю discipline (для класса Max_by_discipline)
         * @param l1 параметр дисциплины, уже имеющийся в коллекции
         * @param l2 заданный параметр дисциплины
         * @return итог сравнения
         */

        @Override
        public int compare(LabWork l1, LabWork l2) {
            int ret = 0;
            double r1 = l1.getDiscipline().getLectureHours();
            double r2 = l2.getDiscipline().getLectureHours();
            if(Math.round(r1 - r2) > 0) ret = 1;
            if(Math.round(r1 - r2) == 0) ret = 0;
            if(Math.round(r1 - r2) < 0) ret = -1;
            return ret;
        }
    }

    /**
     * Метод для сравнения элементов коллекции по полю discipline (для класса Remove_greater)
     * @param l входящий параметр discipline
     * @return итог сравнения
     */
    @Override
    public int compareTo(LabWork l) {
        int ret = 0;
        if(Math.round(this.getMinimalPoint() - l.getMinimalPoint()) > 0) ret = 1;
        if(Math.round(this.getMinimalPoint() - l.getMinimalPoint()) == 0) ret = 0;
        if(Math.round(this.getMinimalPoint() - l.getMinimalPoint()) < 0) ret = -1;
        return ret;
    }

    @Override
    public String toString() {
        String toString = "Lab:\n   ID: " + this.id + "\n   Name: " + this.name +
                "\n   Coordinates: " + this.coordinates +
                "\n   Minimal point: " + this.minimalPoint + "\n   Maximum point: " + this.maximumPoint +
                "\n   Average point: " + this.averagePoint + "\n   Difficulty: " + this.difficulty +
                "\n   Discipline: " + this.discipline + "\n";
        return toString;
    }
}


