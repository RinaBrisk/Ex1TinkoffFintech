package mainClass;

import documentFormat.FilePDF;
import documentFormat.workbook.Workbook;
import helper.Helper;
import network.PersonsDTO;
import network.RandomApi;
import person.Person;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Helper.createFileResources();

        int numberOfPersons = Person.getNumberOfPersons();
        Person[] persons = new Person[numberOfPersons];

        RandomApi randomApi = new RandomApi();
        randomApi.buildClient();
        randomApi.getSearchRequest(numberOfPersons);

        List<PersonsDTO> personsData = randomApi.getPersonsData();
        Person.setPersonsData(personsData, persons);

        Workbook workbook = new Workbook();
        workbook.workbookCreating(persons);

        FilePDF filePDF = new documentFormat.FilePDF();
        filePDF.filePDFCreating(persons);
    }
}
