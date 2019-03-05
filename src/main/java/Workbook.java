import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

class Workbook {

    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private FileOutputStream fileOut;
    private static final String BOOK_PATH = System.getProperty("user.home") + "\\Desktop\\persons.xlsx";

    void createBook() {

        book = new XSSFWorkbook();
        try {
            fileOut = new FileOutputStream(BOOK_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("File created.Path: " + BOOK_PATH);
        sheet = book.createSheet("Страница 1");
    }

    void fillingInData(Person[] persons) {

        XSSFRow headerRow = sheet.createRow((short) 0);
        createHeaders(headerRow);

        for (int i = 0; i < persons.length; i++) {

            CellContent[] cellContents = new CellContent[14];
            cellContents[0] = new CellContent(persons[i].getName(), CellType.STRING);
            cellContents[1] = new CellContent(persons[i].getSurname(), CellType.STRING);
            cellContents[2] = new CellContent(persons[i].getPatronymic(), CellType.STRING);
            cellContents[3] = new CellContent(String.valueOf(persons[i].getAge()), CellType.NUMERIC);
            cellContents[4] = new CellContent(persons[i].getGender(), CellType.STRING);
            cellContents[5] = new CellContent(persons[i].getDateOfBirth(), CellType.STRING);
            cellContents[6] = new CellContent(String.valueOf(persons[i].getITP()), CellType.NUMERIC);
            cellContents[7] = new CellContent(String.valueOf(persons[i].getZipCode()), CellType.NUMERIC);
            cellContents[8] = new CellContent(persons[i].getCountry(), CellType.STRING);
            cellContents[9] = new CellContent(persons[i].getArea(), CellType.STRING);
            cellContents[10] = new CellContent(persons[i].getCity(), CellType.STRING);
            cellContents[11] = new CellContent(persons[i].getStreet(), CellType.STRING);
            cellContents[12] = new CellContent(String.valueOf(persons[i].getHouse()), CellType.NUMERIC);
            cellContents[13] = new CellContent(String.valueOf(persons[i].getApartment()), CellType.NUMERIC);
            XSSFRow row = sheet.createRow(i + 1);
            writeInBook(row, cellContents);
        }
        for (int i = 0; i < 14; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    private void writeInBook(XSSFRow row, CellContent[] cellContents) {
        for (int i = 0; i < 14; i++) {
            if (cellContents[i] == null) {
                System.out.println("Ошибка. Поле № " + i + " не инициализировано.");
            }
            XSSFCell cell = row.createCell(i);
            cell.setCellType(cellContents[i].getCellType());
            cell.setCellValue(cellContents[i].getName());
        }
    }

    private void createHeaders(XSSFRow headerRow) {

        CellContent[] headerCellContents = new CellContent[]{
                new CellContent("Имя", CellType.STRING),
                new CellContent("Фамилия", CellType.STRING),
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
        XSSFCellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < 14; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellType(headerCellContents[i].getCellType());
            cell.setCellValue(headerCellContents[i].getName());
            cell.setCellStyle(style);
        }
    }

    void closeBook() {
        try {
            book.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
