package documentFormat.workbook;

import org.apache.poi.ss.usermodel.CellType;

class CellContent {

    private String aName;
    private CellType aCellType;

    CellContent(String name, CellType cellType){
        aName = name;
        aCellType = cellType;
    }

    CellType getCellType() {
        return aCellType;
    }

    String getName() {
        return aName;
    }
}
