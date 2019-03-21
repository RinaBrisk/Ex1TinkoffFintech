package ru.company.core.documentFormat;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ru.company.core.utils.Helper;
import ru.company.core.models.Person;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FilePDF {

    private Document document;
    private Font headerFont;
    private Font contentFont;
    private PdfPTable table;

    public void filePDFCreating(List<Person> personsData) {
        this.createFilePDF();
        this.createTable();
        this.fillingInData(personsData);
        this.closeFilePDF();
    }

    private void createFilePDF() {
        document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, new FileOutputStream("persons.pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        System.out.println("Файл создан. Путь: Ex1TinkoffFintech/persons.pdf");
        setFont();
    }

    private void setFont() {
        BaseFont baseFont;

        try {
            baseFont = BaseFont.createFont("src/main/resources/Times_New_Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            headerFont = new Font(baseFont, 10, Font.BOLD);
            contentFont = new Font(baseFont, 10);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        table = new PdfPTable(14);
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{1.1f, 1.1f, 1.3f, 0.5f, 0.5f, 1f, 1.2f, 1f, 1.1f, 1.3f, 1f, 1f, 0.6f, 0.5f});
            //ширина столбцов зависит от количества символов, которые в них записываются, 1f - размер по умолчанию
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        addTableHeader();
    }

    private void addTableHeader() {

        Arrays.stream(Helper.getTableHeaders())
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle, headerFont));
                    table.addCell(header);
                });
    }

    private void fillingInData(List<Person> personsData) {

        for (Person person : personsData) {

            PdfPCell[] personContent = new PdfPCell[14];
            personContent[0] = new PdfPCell(new Phrase(person.getSurname(), contentFont));
            personContent[1] = new PdfPCell(new Phrase(person.getName(), contentFont));
            personContent[2] = new PdfPCell(new Phrase(person.getPatronymic(), contentFont));
            personContent[3] = new PdfPCell(new Phrase(String.valueOf(person.getAge()), contentFont));
            personContent[4] = new PdfPCell(new Phrase(person.getGender(), contentFont));
            personContent[5] = new PdfPCell(new Phrase(person.getDateOfBirth(), contentFont));
            personContent[6] = new PdfPCell(new Phrase(String.valueOf(person.getItp()), contentFont));
            personContent[7] = new PdfPCell(new Phrase(String.valueOf(person.getZipCode()), contentFont));
            personContent[8] = new PdfPCell(new Phrase(person.getCountry(), contentFont));
            personContent[9] = new PdfPCell(new Phrase(person.getArea(), contentFont));
            personContent[10] = new PdfPCell(new Phrase(person.getCity(), contentFont));
            personContent[11] = new PdfPCell(new Phrase(person.getStreet(), contentFont));
            personContent[12] = new PdfPCell(new Phrase(String.valueOf(person.getHouse()), contentFont));
            personContent[13] = new PdfPCell(new Phrase(String.valueOf(person.getApartment()), contentFont));
            writeInDocument(personContent);
        }
    }

    private void writeInDocument(PdfPCell[] pdfPCells) {
        for (PdfPCell pdfPCell1 : pdfPCells) {
            table.addCell(pdfPCell1);
        }
    }

    private void closeFilePDF() {
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }
}
