package ru.company.core;

import ru.company.core.documentFormat.FilePDF;
import ru.company.core.documentFormat.workbook.Workbook;
import ru.company.core.utils.Helper;
import ru.company.core.utils.DataManager;
import ru.company.core.models.Person;

public class Main {

    public static void main(String[] args) {


        //Helper helper = new Helper();
        Helper.createFileResources();

        int numberOfPersons = Person.getNumberOfPersons();

        DataManager dataManager = new DataManager();
        dataManager.getData(numberOfPersons);

        Workbook workbook = new Workbook();
        workbook.workbookCreating(dataManager.getPersonsData());

        FilePDF filePDF = new ru.company.core.documentFormat.FilePDF();
        filePDF.filePDFCreating(dataManager.getPersonsData());

        System.exit(0);
    }
}

