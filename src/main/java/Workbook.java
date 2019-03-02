import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Workbook {

    private static XSSFWorkbook book;
    private static XSSFSheet sheet;
    private static FileOutputStream fileOut;
    private static final String BOOK_PATH = System.getProperty("user.home")+"\\Desktop\\persons.xlsx";
    private static final String RESOURCES_PATH = "src/main/resources/";
    private List<String> maleNames;
    private List<String> maleSurnames;
    private List<String> malePatronymics;
    private List<String> femaleNames;
    private List<String> femaleSurnames;
    private List<String> femalePatronymics;
    private List<String> countries;
    private List<String> areas;
    private List<String> streets;
    private List<String> cities;

    public static void main(String[] args) {

        Workbook workbook = new Workbook();
        workbook.createBook();

        int numberOfPersons = randBetween(1, 30);
        System.out.print("Количество человек: " + numberOfPersons);

        workbook.fillingInData(numberOfPersons);
        workbook.closeBook();
    }

    private void createBook() {

        book = new XSSFWorkbook();
        try {
            fileOut = new FileOutputStream(BOOK_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Файл создан. Путь: " + BOOK_PATH);
        sheet = book.createSheet("Страница 1");
    }

    private void fillingInData(int numberOfPersons){

        XSSFRow headerRow = sheet.createRow((short)0);
        createHeaders(headerRow);

        maleNames = readFromFile(RESOURCES_PATH + "MaleNames.txt");
        maleSurnames = readFromFile(RESOURCES_PATH + "MaleSurnames.txt");
        malePatronymics = readFromFile(RESOURCES_PATH + "MalePatronymics.txt");

        femaleNames = readFromFile(RESOURCES_PATH + "FemaleNames.txt");
        femaleSurnames = readFromFile(RESOURCES_PATH +"FemaleSurnames.txt");
        femalePatronymics = readFromFile(RESOURCES_PATH + "FemalePatronymics.txt");

        countries = readFromFile(RESOURCES_PATH + "Countries.txt");
        areas = readFromFile(RESOURCES_PATH + "Areas.txt");
        streets = readFromFile(RESOURCES_PATH + "Streets.txt");
        cities = readFromFile(RESOURCES_PATH + "Cities.txt");

        for(int i = 0; i < numberOfPersons; i++){

            CellContent[] cellContents = new CellContent[14];
            createGenderContent(cellContents);
            createAgeContent(cellContents);
            createITNContent(cellContents);
            createAddressContent(cellContents);

            XSSFRow row = sheet.createRow((short)i+1);
            writeInBook(row, cellContents);
        }
        for(int i = 0; i < 14; i++){
            sheet.autoSizeColumn(i);
        }
    }

    private void createGenderContent(CellContent[] cellContents){
        int gender = randBetween(0,1);
        if(gender == 0){
            cellContents[NameOfColumn.GENDER.ordinal()] = new CellContent("M", CellType.STRING);
            cellContents[NameOfColumn.NAME.ordinal()] = new CellContent((String)getRandomPosition(maleNames), CellType.STRING);
            cellContents[NameOfColumn.SURNAME.ordinal()] = new CellContent((String)getRandomPosition(maleSurnames), CellType.STRING);
            cellContents[NameOfColumn.PATRONYMIC.ordinal()] = new CellContent((String)getRandomPosition(malePatronymics), CellType.STRING);
        }
        if(gender == 1){
            cellContents[NameOfColumn.GENDER.ordinal()] = new CellContent("Ж", CellType.STRING);
            cellContents[NameOfColumn.NAME.ordinal()] = new CellContent((String)getRandomPosition(femaleNames), CellType.STRING);
            cellContents[NameOfColumn.SURNAME.ordinal()] = new CellContent((String)getRandomPosition(femaleSurnames), CellType.STRING);
            cellContents[NameOfColumn.PATRONYMIC.ordinal()] = new CellContent((String)getRandomPosition(femalePatronymics), CellType.STRING);
        }
    }

    private void createAgeContent(CellContent[] cellContents){

        GregorianCalendar dateOfBirth = new GregorianCalendar();
        int yearBirth = randBetween(1919, 2019);
        dateOfBirth.set(Calendar.YEAR, yearBirth);
        int dayOfYear = randBetween(1, dateOfBirth.getActualMaximum(Calendar.DAY_OF_YEAR));
        dateOfBirth.set(Calendar.DAY_OF_YEAR, dayOfYear);
        String dayBirth = String.valueOf(dateOfBirth.get(Calendar.DAY_OF_MONTH));
        String monthBirth = String.valueOf(dateOfBirth.get(Calendar.MONTH) + 1);
        if(Integer.valueOf(dayBirth) < 10){
            dayBirth = "0" + dayBirth;
        }
        if(Integer.valueOf(monthBirth) < 10){
            monthBirth = "0" + monthBirth;
        }
        cellContents[NameOfColumn.DATE_OF_BIRTH.ordinal()] = new CellContent(dayBirth + "-"
                                                                                + monthBirth + "-"
                                                                                + dateOfBirth.get(Calendar.YEAR),CellType.STRING );
        cellContents[NameOfColumn.AGE.ordinal()] = new CellContent(Integer.toString(calcAge(dateOfBirth)), CellType.NUMERIC);
    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private int calcAge(GregorianCalendar dateOfBirth){
        Calendar currentDate = Calendar.getInstance();
        int years = currentDate.get(GregorianCalendar.YEAR) - dateOfBirth.get(GregorianCalendar.YEAR);
        // корректируем, если текущий месяц в дате проверки меньше месяца даты рождения
        int currentMonth = currentDate.get(GregorianCalendar.MONTH);
        int birthMonth = dateOfBirth.get(GregorianCalendar.MONTH);
        if (currentMonth < birthMonth ) {
            years --;
        } else  if (currentMonth == birthMonth
                && currentDate.get(GregorianCalendar.DAY_OF_MONTH) < dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH)) {
            // отдельный случай - в случае равенства месяцев,
            // но меньшего дня месяца в дате проверки - корректируем
            years --;
        }
        return years;
    }

    private void createAddressContent(CellContent[] cellContents){
        cellContents[NameOfColumn.ZIP_CODE.ordinal()] = new CellContent(String.valueOf(randBetween(100000, 200000)), CellType.NUMERIC);
        cellContents[NameOfColumn.COUNTRY.ordinal()] = new CellContent((String)getRandomPosition(countries), CellType.STRING);
        cellContents[NameOfColumn.AREA.ordinal()] = new CellContent((String)getRandomPosition(areas), CellType.STRING);
        cellContents[NameOfColumn.CITY.ordinal()] = new CellContent((String)getRandomPosition(cities), CellType.STRING);
        cellContents[NameOfColumn.STREET.ordinal()] = new CellContent((String)getRandomPosition(streets), CellType.STRING);
        cellContents[NameOfColumn.HOUSE.ordinal()] = new CellContent(String.valueOf(randBetween(0, 200)), CellType.NUMERIC);
        cellContents[NameOfColumn.APARTMENT.ordinal()] = new CellContent(String.valueOf(randBetween(0, 500)),CellType.NUMERIC);
    }

    private void createITNContent(CellContent[] cellContents){
        String codeTaxAuthority = String.valueOf(randBetween(7700, 7751));
        String ordinalNumber = String.valueOf(randBetween(0, 9));
        for(int i = 0; i < 5; i++){
            ordinalNumber = ordinalNumber + randBetween(0, 9);
        }
        String controlNumber = String.valueOf(randBetween(0, 9));
        controlNumber = controlNumber + randBetween(0, 9);
        cellContents[NameOfColumn.ITN.ordinal()] = new CellContent(codeTaxAuthority + ordinalNumber + controlNumber, CellType.NUMERIC);
    }

    private Object getRandomPosition(List<String> positions){
        int randomPosition = (int) (Math.random() * positions.size() - 1);
        return positions.get(randomPosition);
    }

    private List<String> readFromFile(String fileName){
        List<String> lines = new ArrayList<>();
        try {
            lines.addAll(Files.readAllLines(Paths.get(fileName), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
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

        CellContent[] cellContents = new CellContent[]{
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
            cell.setCellType(cellContents[i].getCellType());
            cell.setCellValue(cellContents[i].getName());
            cell.setCellStyle(style);
        }
    }

    private void closeBook(){
        try {
            book.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
