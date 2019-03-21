package ru.company.core.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Helper {

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
    private static final String[] tableHeaders = {"Фамилия", "Имя", "Отчество", "Возраст", "Пол(м/ж)",
            "Дата рождения", "ИНН", "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира"};
    private static List<String> mySqlConnection;

    public static List<String> getMaleNames() {
        return maleNames;
    }
    public static List<String> getMaleSurnames() { return maleSurnames; }
    public static List<String> getMalePatronymics() {
        return malePatronymics;
    }
    public static List<String> getFemaleNames() {
        return femaleNames;
    }
    public static List<String> getFemaleSurnames() {
        return femaleSurnames;
    }
    public static List<String> getFemalePatronymics() {
        return femalePatronymics;
    }
    public static List<String> getCountries() {
        return countries;
    }
    public static List<String> getAreas() {
        return areas;
    }
    public static List<String> getStreets() {
        return streets;
    }
    public static List<String> getCities() {
        return cities;
    }
    public static String[] getTableHeaders() {
        return tableHeaders;
    }
    public static String getMySqlConnection(int i) {
        return mySqlConnection.get(i);
    }

    public static void createFileResources() {

        try {
            maleNames = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("MaleNames.txt")).toURI())));
            maleSurnames = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("MaleSurnames.txt")).toURI())));
            malePatronymics = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("MalePatronymics.txt")).toURI())));

            femaleNames = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("FemaleNames.txt")).toURI())));
            femaleSurnames = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("FemaleSurnames.txt")).toURI())));
            femalePatronymics = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("FemalePatronymics.txt")).toURI())));

            countries = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("Countries.txt")).toURI())));
            areas = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("Areas.txt")).toURI())));
            streets = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("Streets.txt")).toURI())));
            cities = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("Cities.txt")).toURI())));

            mySqlConnection = readFromFile(String.valueOf(Paths.get(Objects.requireNonNull(Helper.class.getClassLoader().getResource("MySqlConnection.txt")).toURI())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines.addAll(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    static String gregorianCalendarToString(GregorianCalendar date) {
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

    static int calcAge(GregorianCalendar dateOfBirth) {
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

    public static Object getRandomPosition(List<String> positions) {
        int randomPosition = (int) (Math.random() * positions.size() - 1);
        return positions.get(randomPosition);
    }

    static int calcControlNumber(int[] coeff, int[] itn) {
        int sum = 0;
        for (int i = 0; i < coeff.length; i++) {
            sum = sum + itn[i] * coeff[i];
        }
        int controlNum = sum % 11;
        if (sum % 11 == 10) return 0;
        else return controlNum;
    }

    public static String createCorrectFormatDateOfBirth(String date) {
        int[] correctOrder = {8, 9, 7, 5, 6, 4, 0, 1, 2, 3};
        StringBuilder dateConnectFormat = new StringBuilder();
        for (int i : correctOrder) {
            dateConnectFormat.append(date.charAt(i));
        }
        return (String.valueOf(dateConnectFormat));
    }

    public static String setFirstCapitalLetter(String word) {
        if (word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String deleteNumbersFromString(String str) {
        return str.replaceAll("[^A-Za-zА-Яа-я]", "");
    }
}
