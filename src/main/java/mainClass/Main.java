package mainClass;

import documentFormat.FilePDF;
import documentFormat.workbook.Workbook;
import helper.Helper;
import helper.DataManager;
import person.Person;

public class Main {

    public static void main(String[] args) {

        Helper.createFileResources();

        int numberOfPersons = Person.getNumberOfPersons();

        DataManager dataManager = new DataManager();
        dataManager.getData(numberOfPersons);

        Workbook workbook = new Workbook();
        workbook.workbookCreating(dataManager.getPersonsData());

        FilePDF filePDF = new documentFormat.FilePDF();
        filePDF.filePDFCreating(dataManager.getPersonsData());

        System.exit(0);
    }
}

