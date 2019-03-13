package mainClass;

import helper.Helper;
import network.PersonsDTO;
import person.DataAcquisition;
import person.Person;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Helper.createFileResources();

        int numberOfPersons = Person.getNumberOfPersons();
        Person[] persons = new Person[numberOfPersons];

        DataAcquisition dataAcquisition = new DataAcquisition();
        List<PersonsDTO> personsData = dataAcquisition.getDataFromAPI(numberOfPersons);
        if (personsData == null) {
            //          personsData = dataAcquisition.getDataFromDatabase();
            //         if(personsData == null){
            //загрузить из файла
        } else {

            //записать в БД
            //загрузить из API
        }
    }

//        Person.setPersonsData(personsData, persons);

//        Workbook workbook = new Workbook();
//        workbook.workbookCreating(persons);
//
//        FilePDF filePDF = new documentFormat.FilePDF();
//        filePDF.filePDFCreating(persons);
//
//        System.exit(0);

}
            }
