package ru.company.core.documentFormat.workbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import ru.company.core.models.Person;

import java.io.*;
import java.util.List;

public class Workbook {

    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private FileOutputStream fileOut;
  //  private static final String BOOK_PATH = System.getProperty("user.home") + "\\Desktop\\persons.xlsx";

    public void workbookCreating(List<Person> personsData) {
        this.createBook();
        this.headerCreating();
        this.fillingInData(personsData);
        this.closeBook();
    }

    private void createBook() {

        book = new XSSFWorkbook();
        try {
            fileOut = new FileOutputStream("persons.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Файл создан. Путь: Ex1TinkoffFintech/persons.xlsx");
        sheet = book.createSheet("Страница 1");
    }

    private void fillingInData(List<Person> personsData) {

        int rownum = 1;
        for (Person person : personsData) {

            CellContent[] cellContents = new CellContent[14];
            cellContents[0] = new CellContent(person.getSurname(), CellType.STRING);
            cellContents[1] = new CellContent(person.getName(), CellType.STRING);
            cellContents[2] = new CellContent(person.getPatronymic(), CellType.STRING);
            cellContents[3] = new CellContent(String.valueOf(person.getAge()), CellType.NUMERIC);
            cellContents[4] = new CellContent(person.getGender(), CellType.STRING);
            cellContents[5] = new CellContent(person.getDateOfBirth(), CellType.STRING);
            cellContents[6] = new CellContent(String.valueOf(person.getItp()), CellType.NUMERIC);
            cellContents[7] = new CellContent(String.valueOf(person.getZipCode()), CellType.NUMERIC);
            cellContents[8] = new CellContent(person.getCountry(), CellType.STRING);
            cellContents[9] = new CellContent(person.getArea(), CellType.STRING);
            cellContents[10] = new CellContent(person.getCity(), CellType.STRING);
            cellContents[11] = new CellContent(person.getStreet(), CellType.STRING);
            cellContents[12] = new CellContent(String.valueOf(person.getHouse()), CellType.NUMERIC);
            cellContents[13] = new CellContent(String.valueOf(person.getApartment()), CellType.NUMERIC);
            XSSFRow row = sheet.createRow(rownum);
            rownum++;
            writeInBook(row, cellContents);
        }
        for (int i = 0; i < 14; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    private void writeInBook(XSSFRow row, CellContent[] cellContents) {
        for (int i = 0; i < 14; i++) {
            if ((cellContents[i].getName().equals("")) || (cellContents[i].getCellType() == null)) {
                System.out.println("Ошибка. Поле № " + i + " не инициализировано.");
            }
            XSSFCell cell = row.createCell(i);
            cell.setCellType(cellContents[i].getCellType());
            cell.setCellValue(cellContents[i].getName());
        }
    }

    private void headerCreating() {
        CellContent[] headerCellContent = createHeaderContent();
        XSSFRow headerRow = sheet.createRow((short) 0);
        setHeader(createHeaderStyle(), headerRow, headerCellContent);
    }

    private CellContent[] createHeaderContent() {
        return new CellContent[]{
                new CellContent("Фамилия", CellType.STRING),
                new CellContent("Имя", CellType.STRING),
                new CellContent("Отчество", CellType.STRING),
                new CellContent("Возраст", CellType.NUMERIC),
                new CellContent("Пол(м/ж)", CellType.STRING),
                new CellContent("Дата рождения", CellType.STRING),
                new CellContent("ИНН", CellType.NUMERIC),
                new CellContent("Почтовый индекс", CellType.NUMERIC),
                new CellContent("Страна", CellType.STRING),
                new CellContent("Область", CellType.STRING),
                new CellContent("Город", CellType.STRING),
                new CellContent("Улица", CellType.STRING),
                new CellContent("Дом", CellType.NUMERIC),
                new CellContent("Квартира", CellType.NUMERIC)
        };
    }

    private XSSFCellStyle createHeaderStyle() {
        XSSFCellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private void setHeader(XSSFCellStyle style, XSSFRow headerRow, CellContent[] headerCellContent) {
        for (int i = 0; i < 14; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellType(headerCellContent[i].getCellType());
            cell.setCellValue(headerCellContent[i].getName());
            cell.setCellStyle(style);
        }
    }

    private void closeBook() {
        try {
            book.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
