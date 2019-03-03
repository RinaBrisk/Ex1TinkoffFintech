import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.GregorianCalendar;

class FilePDF {

    private Document document;
    private static final String DOCUMENT_PATH = System.getProperty("user.home") + "\\Desktop\\persons.pdf";
    private Font headerFont;
    private Font contentFont;
    private PdfPTable table;

    void createFilePDF() {
        document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, new FileOutputStream(DOCUMENT_PATH));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        BaseFont baseFont;
        try {
            baseFont = BaseFont.createFont("src/main/resources/Times_New_Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            headerFont = new Font(baseFont, 10, Font.BOLD);
            contentFont = new Font(baseFont, 10);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Файл создан. Путь: " + DOCUMENT_PATH);
    }

    void createTable() {
        table = new PdfPTable(14);
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{1.1f, 1.1f, 1.3f, 0.5f, 0.5f, 1f, 1.2f, 1f, 1.1f, 1.3f, 1f, 1f, 0.6f, 0.5f});
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        addTableHeader();
    }

    private void addTableHeader() {

        Arrays.stream(PersonTable.getTableHeaders())
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle, headerFont));
                    table.addCell(header);
                });
    }

    void fillingInData(int numberOfPersons) {

        for (int i = 0; i < numberOfPersons; i++) {
            PdfPCell[] personContent = new PdfPCell[14];
            setGenderContent(personContent);
            setAgeContent(personContent);
            setAddressContent(personContent);
            setITNContent(personContent);
            setPersonContent(personContent);
        }
    }

    private void setPersonContent(PdfPCell[] personContent) {
        Arrays.stream(personContent).forEachOrdered(pdfPCell -> table.addCell(pdfPCell));
    }

    private void setGenderContent(PdfPCell[] personContent) {
        int gender = PersonTable.randBetween(0, 1);
        if (gender == 0) {
            personContent[NameOfColumn.GENDER.ordinal()] = new PdfPCell(new Phrase("M", contentFont));
            personContent[NameOfColumn.NAME.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getMaleNames()), contentFont));
            personContent[NameOfColumn.SURNAME.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getMaleSurnames()), contentFont));
            personContent[NameOfColumn.PATRONYMIC.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getMalePatronymics()), contentFont));
        }
        if (gender == 1) {
            personContent[NameOfColumn.GENDER.ordinal()] = new PdfPCell(new Phrase("Ж", contentFont));
            personContent[NameOfColumn.NAME.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getFemaleNames()), contentFont));
            personContent[NameOfColumn.SURNAME.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getFemaleSurnames()), contentFont));
            personContent[NameOfColumn.PATRONYMIC.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getFemalePatronymics()), contentFont));
        }
    }

    private void setAgeContent(PdfPCell[] personContent) {
        GregorianCalendar dateOfBirth = PersonTable.createAgeContent();
        personContent[NameOfColumn.DATE_OF_BIRTH.ordinal()] = new PdfPCell(new Phrase(PersonTable.gregorianCalendarToString(dateOfBirth), contentFont));
        personContent[NameOfColumn.AGE.ordinal()] = new PdfPCell(new Phrase(String.valueOf(PersonTable.calcAge(dateOfBirth)), contentFont));
    }

    private void setAddressContent(PdfPCell[] personContent) {
        personContent[NameOfColumn.ZIP_CODE.ordinal()] = new PdfPCell(new Phrase(String.valueOf(PersonTable.randBetween(100000, 200000)), contentFont));
        personContent[NameOfColumn.COUNTRY.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getCountries()), contentFont));
        personContent[NameOfColumn.AREA.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getAreas()), contentFont));
        personContent[NameOfColumn.CITY.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getCities()), contentFont));
        personContent[NameOfColumn.STREET.ordinal()] = new PdfPCell(new Phrase((String) PersonTable.getRandomPosition(PersonTable.getStreets()), contentFont));
        personContent[NameOfColumn.HOUSE.ordinal()] = new PdfPCell(new Phrase(String.valueOf(PersonTable.randBetween(0, 200)), contentFont));
        personContent[NameOfColumn.APARTMENT.ordinal()] = new PdfPCell(new Phrase(String.valueOf(PersonTable.randBetween(0, 500)), contentFont));
    }

    private void setITNContent(PdfPCell[] personContent) {
        String ITN = PersonTable.createITNContent();
        personContent[NameOfColumn.ITN.ordinal()] = new PdfPCell(new Phrase(ITN, contentFont));
    }

    void closeFilePDF() {
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }
}
