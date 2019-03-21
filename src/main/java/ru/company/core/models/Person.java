package ru.company.core.models;

import ru.company.core.utils.DataGeneration;
import ru.company.core.utils.Helper;
import ru.company.core.network.PersonsDTO;

public class Person {
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private String gender;
    private String dateOfBirth;
    private String itp;
    private int zipCode;
    private String country;
    private String area;
    private String city;
    private String street;
    private int house;
    private int apartment;

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPatronymic() {
        return patronymic;
    }
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getItp() {
        return itp;
    }
    public int getZipCode() {
        return zipCode;
    }
    public String getCountry() {
        return country;
    }
    public String getArea() {
        return area;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public int getHouse() {
        return house;
    }
    public int getApartment() {
        return apartment;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setItp(String itp) {
        this.itp = itp;
    }
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setHouse(int house) {
        this.house = house;
    }
    public void setApartment(int apartment) {
        this.apartment = apartment;
    }
    public static int getNumberOfPersons() {
        return Helper.randBetween(1, 30);
    }

    public void setDataFromResources() {
            DataGeneration.createGenderContent(this);
            DataGeneration.createAgeContent(this);
            DataGeneration.createITNContent(this);
            DataGeneration.createAddressContent(this);
    }

    public void setAPIGenderContent(PersonsDTO personDTO) {

        this.setName(Helper.setFirstCapitalLetter(personDTO.getName().getFirst()));
        this.setSurname(Helper.setFirstCapitalLetter(personDTO.getName().getLast()));

        if (personDTO.getGender().equals("male")) {
            this.setGender("М");
            this.setPatronymic((String) Helper.getRandomPosition(Helper.getMalePatronymics()));
        } else {
            this.setGender("Ж");
            this.setPatronymic((String) Helper.getRandomPosition(Helper.getFemalePatronymics()));
        }
    }

    public void setAPIAgeContent(PersonsDTO personDTO) {
        this.setDateOfBirth(
                Helper.createCorrectFormatDateOfBirth(
                        personDTO.getDateOfBirth().getDate()));
        this.setAge(personDTO.getDateOfBirth().getAge());
    }

    public void setAPIAddressContent(PersonsDTO personDTO) {
        this.setCountry(Helper.setFirstCapitalLetter((String)Helper.getRandomPosition(Helper.getCountries())));
        this.setZipCode(Helper.randBetween(100000, 200000));
        this.setArea(Helper.setFirstCapitalLetter(personDTO.getLocation().getArea()));
        this.setCity(Helper.setFirstCapitalLetter(personDTO.getLocation().getCity()));
        this.setStreet(Helper.setFirstCapitalLetter(
                Helper.deleteNumbersFromString(personDTO.getLocation().getStreet())));
        this.setHouse(Helper.randBetween(1, 200));
        this.setApartment(Helper.randBetween(1, 500));
    }
}
