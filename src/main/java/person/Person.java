package person;

import generation.DataGeneration;
import helper.Helper;
import network.PersonsDTO;

import java.util.List;

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

    public static void setPersonsData(List<PersonsDTO> personsData, Person[] persons){
        if(personsData != null){
            for (int i = 0; i < persons.length; i++) {
                persons[i] = new Person();
                persons[i].setDataFromRandomAPI(personsData.get(i));
            }
        }else{
            for (int i = 0; i < persons.length; i++) {
                persons[i] = new Person();
                persons[i].setDataFromResources();
            }
        }
    }

    private void setDataFromRandomAPI(PersonsDTO personData) {
        setAPIGenderContent(this, personData);
        setAPIAgeContent(this, personData);
        DataGeneration.createITNContent(this);
        setAPIAddressContent(this, personData);
    }

    private void setDataFromResources() {
        DataGeneration.createGenderContent(this);
        DataGeneration.createAgeContent(this);
        DataGeneration.createITNContent(this);
        DataGeneration.createAddressContent(this);
    }

    private void setAPIGenderContent(Person person, PersonsDTO personDTO) {

        person.setName(Helper.setFirstCapitalLetter(personDTO.getName().getFirst()));
        person.setSurname(Helper.setFirstCapitalLetter(personDTO.getName().getLast()));

        if (personDTO.getGender().equals("male")) {
            person.setGender("М");
            person.setPatronymic((String) Helper.getRandomPosition(Helper.getMalePatronymics()));
        } else {
            person.setGender("Ж");
            person.setPatronymic((String) Helper.getRandomPosition(Helper.getFemalePatronymics()));
        }
    }

    private void setAPIAgeContent(Person person, PersonsDTO personDTO) {
        person.setDateOfBirth(
                Helper.createCorrectFormatDateOfBirth(
                        personDTO.getDateOfBirth().getDate()));
        person.setAge(personDTO.getDateOfBirth().getAge());
    }

    private void setAPIAddressContent(Person person, PersonsDTO personDTO) {
        person.setCountry((String)Helper.getRandomPosition(Helper.getCountries()));
        person.setZipCode(Helper.randBetween(100000, 200000));
        person.setArea(Helper.setFirstCapitalLetter(personDTO.getLocation().getArea()));
        person.setCity(Helper.setFirstCapitalLetter(personDTO.getLocation().getCity()));
        person.setStreet(Helper.setFirstCapitalLetter(
                        Helper.deleteNumbersFromString(personDTO.getLocation().getStreet())));
        person.setHouse(Helper.randBetween(1, 200));
        person.setApartment(Helper.randBetween(1, 500));
    }

}
