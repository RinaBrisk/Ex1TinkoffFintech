
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataGeneration {

    private static List<String> maleNames;
    private static List<String> maleSurnames;
    private static List<String> malePatronymics;
    private static List<String> femaleNames;
    private static List<String> femaleSurnames;
    private static List<String> femalePatronymics;
    private static List<String> countries;
    private static List<String> areas;
    private static List<String> streets;
    private static List<String> cities;
    private static final String RESOURCES_PATH = "src/main/resources/";
    private static Person[] persons;
    private static final String[] tableHeaders = {"Имя", "Фамилия", "Отчество", "Возраст", "Пол(м/ж)",
            "Дата рождения", "ИНН", "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира"};

    static String[] getTableHeaders() {
        return tableHeaders;
    }

    public static void main(String[] args) {

        createFileResources();
        int numberOfPersons = randBetween(1, 30);
        persons = new Person[numberOfPersons];

        persons = getDataAboutPersons();

        Workbook workbook = new Workbook();
        workbook.createBook();
        workbook.fillingInData(persons);
        workbook.closeBook();

        FilePDF filePDF = new FilePDF();
        filePDF.createFilePDF();
        filePDF.createTable();
        filePDF.fillingInData(persons);
        filePDF.closeFilePDF();
    }

    private static void createFileResources() {
        maleNames = readFromFile(RESOURCES_PATH + "MaleNames.txt");
        maleSurnames = readFromFile(RESOURCES_PATH + "MaleSurnames.txt");
        malePatronymics = readFromFile(RESOURCES_PATH + "MalePatronymics.txt");

        femaleNames = readFromFile(RESOURCES_PATH + "FemaleNames.txt");
        femaleSurnames = readFromFile(RESOURCES_PATH + "FemaleSurnames.txt");
        femalePatronymics = readFromFile(RESOURCES_PATH + "FemalePatronymics.txt");

        countries = readFromFile(RESOURCES_PATH + "Countries.txt");
        areas = readFromFile(RESOURCES_PATH + "Areas.txt");
        streets = readFromFile(RESOURCES_PATH + "Streets.txt");
        cities = readFromFile(RESOURCES_PATH + "Cities.txt");
    }

    private static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines.addAll(Files.readAllLines(Paths.get(fileName), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static Person[] getDataAboutPersons() {
        for (int i = 0; i < persons.length; i++) {
            persons[i] = new Person();
            createGenderContent(persons[i]);
            createAgeContent(persons[i]);
            createITNContent(persons[i]);
            createAddressContent(persons[i]);
        }
        return persons;
    }

    private static void createGenderContent(Person person) {
        int gender = DataGeneration.randBetween(0, 1);
        if (gender == 0) {
            person.setGender("M");
            person.setName((String) getRandomPosition(maleNames));
            person.setSurname((String) getRandomPosition(maleSurnames));
            person.setPatronymic((String) getRandomPosition(malePatronymics));
        }
        if (gender == 1) {
            person.setGender("Ж");
            person.setName((String) getRandomPosition(femaleNames));
            person.setSurname((String) getRandomPosition(femaleSurnames));
            person.setPatronymic((String) getRandomPosition(femalePatronymics));
        }
    }

    private static void createAgeContent(Person person) {
        GregorianCalendar dateOfBirth = new GregorianCalendar();
        int yearBirth = randBetween(1919, 2019);
        dateOfBirth.set(Calendar.YEAR, yearBirth);
        int dayOfYear = randBetween(1, dateOfBirth.getActualMaximum(Calendar.DAY_OF_YEAR));
        dateOfBirth.set(Calendar.DAY_OF_YEAR, dayOfYear);
        person.setDateOfBirth(gregorianCalendarToString(dateOfBirth));
        person.setAge(calcAge(dateOfBirth));
    }

    private static String gregorianCalendarToString(GregorianCalendar date) {
        String dayBirth = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        String monthBirth = String.valueOf(date.get(Calendar.MONTH) + 1);
        if (Integer.valueOf(dayBirth) < 10) {
            dayBirth = "0" + dayBirth;
        }
        if (Integer.valueOf(monthBirth) < 10) {
            monthBirth = "0" + monthBirth;
        }
        return dayBirth + "-" + monthBirth + "-" + date.get(Calendar.YEAR);
    }

    private static int calcAge(GregorianCalendar dateOfBirth) {
        Calendar currentDate = Calendar.getInstance();
        int years = currentDate.get(GregorianCalendar.YEAR) - dateOfBirth.get(GregorianCalendar.YEAR);
        // корректируем, если текущий месяц в дате проверки меньше месяца даты рождения
        int currentMonth = currentDate.get(GregorianCalendar.MONTH);
        int birthMonth = dateOfBirth.get(GregorianCalendar.MONTH);
        if (currentMonth < birthMonth) {
            years--;
        } else if (currentMonth == birthMonth
                && currentDate.get(GregorianCalendar.DAY_OF_MONTH) < dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH)) {
            // отдельный случай - в случае равенства месяцев,
            // но меньшего дня месяца в дате проверки - корректируем
            years--;
        }
        return years;
    }

    private static void createITNContent(Person person) {
        String codeTaxAuthority = String.valueOf(randBetween(7700, 7751));
        StringBuilder ordinalNumber = new StringBuilder(String.valueOf(randBetween(0, 9)));
        for (int i = 0; i < 5; i++) {
            ordinalNumber.append(randBetween(0, 9));
        }
        String controlNumber = String.valueOf(randBetween(0, 9));
        controlNumber = controlNumber + randBetween(0, 9);
        person.setITP(codeTaxAuthority + ordinalNumber + controlNumber);
    }

    private static void createAddressContent(Person person) {
        person.setZipCode(randBetween(100000, 200000));
        person.setCountry((String) getRandomPosition(countries));
        person.setArea((String) getRandomPosition(areas));
        person.setCity((String) getRandomPosition(cities));
        person.setStreet((String) getRandomPosition(streets));
        person.setHouse(randBetween(0, 200));
        person.setApartment(randBetween(0, 500));
    }

    private static Object getRandomPosition(List<String> positions) {
        int randomPosition = (int) (Math.random() * positions.size() - 1);
        return positions.get(randomPosition);
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

}
