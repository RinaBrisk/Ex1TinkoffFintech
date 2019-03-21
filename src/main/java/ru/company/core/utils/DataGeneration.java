package ru.company.core.utils;

import ru.company.core.models.Person;
import java.util.*;
import static ru.company.core.utils.Helper.randBetween;

public class DataGeneration {

    public static void createGenderContent(Person person) {
        int gender = randBetween(0, 1);
        if (gender == 0) {
            person.setGender("M");
            person.setName((String) Helper.getRandomPosition(Helper.getMaleNames()));
            person.setSurname((String) Helper.getRandomPosition(Helper.getMaleSurnames()));
            person.setPatronymic((String) Helper.getRandomPosition(Helper.getMalePatronymics()));
        }
        if (gender == 1) {
            person.setGender("Ж");
            person.setName((String) Helper.getRandomPosition(Helper.getFemaleNames()));
            person.setSurname((String) Helper.getRandomPosition(Helper.getFemaleSurnames()));
            person.setPatronymic((String) Helper.getRandomPosition(Helper.getFemalePatronymics()));
        }
    }

    public static void createAgeContent(Person person) {
        GregorianCalendar dateOfBirth = new GregorianCalendar();
        int yearBirth = randBetween(1919, 2019);
        dateOfBirth.set(Calendar.YEAR, yearBirth);
        int dayOfYear = randBetween(1, dateOfBirth.getActualMaximum(Calendar.DAY_OF_YEAR));
        dateOfBirth.set(Calendar.DAY_OF_YEAR, dayOfYear);
        person.setDateOfBirth(Helper.gregorianCalendarToString(dateOfBirth));
        person.setAge(Helper.calcAge(dateOfBirth));
    }


    public static void createITNContent(Person person) {
        int[] itn = new int[12];
        itn[0] = 7; //код региона: 77
        itn[1] = 7;
        for (int i = 2; i < 10; i++) {
            itn[i] = randBetween(0, 9);
        }
        int[] coeff1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        int[] coeff2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        itn[10] = Helper.calcControlNumber(coeff1, itn);
        itn[11] = Helper.calcControlNumber(coeff2, itn);
        StringBuilder itnStr = new StringBuilder();
        Arrays.stream(itn).forEachOrdered((i) ->
                itnStr.append(i).toString());
        person.setItp(itnStr.toString());
    }


    public static void createAddressContent(Person person) {
        person.setZipCode(randBetween(100000, 200000));
        person.setCountry((String) Helper.getRandomPosition(Helper.getCountries()));
        person.setArea((String) Helper.getRandomPosition(Helper.getAreas()));
        person.setCity((String) Helper.getRandomPosition(Helper.getCities()));
        person.setStreet((String) Helper.getRandomPosition(Helper.getStreets()));
        person.setHouse(randBetween(1, 200));
        person.setApartment(randBetween(1, 500));
    }
}
