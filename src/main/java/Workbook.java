import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

class Workbook {

    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private FileOutputStream fileOut;
    private static final String BOOK_PATH = System.getProperty("user.home")+"\\Desktop\\persons.xlsx";

    void createBook() {

        book = new XSSFWorkbook();
        try {
            fileOut = new FileOutputStream(BOOK_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Файл создан. Путь: " + BOOK_PATH);
        sheet = book.createSheet("Страница 1");
    }

    void fillingInData(int numberOfPersons){

        XSSFRow headerRow = sheet.createRow((short)0);
        createHeaders(headerRow);

        for(int i = 0; i < numberOfPersons; i++){

            CellContent[] cellContents = new CellContent[14];
            setGenderContent(cellContents);
            setAgeContent(cellContents);
            setITNContent(cellContents);
            setAddressContent(cellContents);

            XSSFRow row = sheet.createRow((short)i+1);
            writeInBook(row, cellContents);
        }
        for(int i = 0; i < 14; i++){
            sheet.autoSizeColumn(i);
        }
    }

    private void setGenderContent(CellContent[] cellContents){
        int gender = PersonTable.randBetween(0,1);
        if(gender == 0){
            cellContents[NameOfColumn.GENDER.ordinal()] = new CellContent("M", CellType.STRING);
            cellContents[NameOfColumn.NAME.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getMaleNames()), CellType.STRING);
            cellContents[NameOfColumn.SURNAME.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getMaleSurnames()), CellType.STRING);
            cellContents[NameOfColumn.PATRONYMIC.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getMalePatronymics()), CellType.STRING);
        }
        if(gender == 1){
            cellContents[NameOfColumn.GENDER.ordinal()] = new CellContent("Ж", CellType.STRING);
            cellContents[NameOfColumn.NAME.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getFemaleNames()), CellType.STRING);
            cellContents[NameOfColumn.SURNAME.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getFemaleSurnames()), CellType.STRING);
            cellContents[NameOfColumn.PATRONYMIC.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getFemalePatronymics()), CellType.STRING);
        }
    }

    private void setAgeContent(CellContent[] cellContents){
        GregorianCalendar dateOfBirth = PersonTable.createAgeContent();
        cellContents[NameOfColumn.DATE_OF_BIRTH.ordinal()] = new CellContent(PersonTable.gregorianCalendarToString(dateOfBirth), CellType.STRING);
        cellContents[NameOfColumn.AGE.ordinal()] = new CellContent(Integer.toString(PersonTable.calcAge(dateOfBirth)), CellType.NUMERIC);
    }

    private void setAddressContent(CellContent[] cellContents){
        cellContents[NameOfColumn.ZIP_CODE.ordinal()] = new CellContent(String.valueOf(PersonTable.randBetween(100000, 200000)), CellType.NUMERIC);
        cellContents[NameOfColumn.COUNTRY.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getCountries()), CellType.STRING);
        cellContents[NameOfColumn.AREA.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getAreas()), CellType.STRING);
        cellContents[NameOfColumn.CITY.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getCities()), CellType.STRING);
        cellContents[NameOfColumn.STREET.ordinal()] = new CellContent((String)PersonTable.getRandomPosition(PersonTable.getStreets()), CellType.STRING);
        cellContents[NameOfColumn.HOUSE.ordinal()] = new CellContent(String.valueOf(PersonTable.randBetween(0, 200)), CellType.NUMERIC);
        cellContents[NameOfColumn.APARTMENT.ordinal()] = new CellContent(String.valueOf(PersonTable.randBetween(0, 500)),CellType.NUMERIC);
    }

    private void setITNContent(CellContent[] cellContents){
        String ITN = PersonTable.createITNContent();
        cellContents[NameOfColumn.ITN.ordinal()] = new CellContent(ITN, CellType.NUMERIC);
    }

    private void writeInBook(XSSFRow row, CellContent[] cellContents){
        for(int i = 0; i < 14; i++){
            if(cellContents[i] == null){
                System.out.println("Ошибка. Поле № " + i + " не инициализировано.");
            }
            XSSFCell cell = row.createCell(i);
            cell.setCellType(cellContents[i].getCellType());
            cell.setCellValue(cellContents[i].getName());
        }
    }

    private void createHeaders(XSSFRow headerRow){

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

        for(int i = 0; i < 14; i++){
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellType(headerCellContents[i].getCellType());
            cell.setCellValue(headerCellContents[i].getName());
            cell.setCellStyle(style);
        }
    }

    void closeBook(){
        try {
            book.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
