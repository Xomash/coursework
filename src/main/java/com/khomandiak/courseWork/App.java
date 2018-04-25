package com.khomandiak.courseWork;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class App {

    private static String pathname = "C:\\Users\\o.khomandiak\\Documents\\template_manual_sending.xlsx";

    public static void main(String[] args) throws IOException {
        List<Message> messages = readMessagesFromExcelFile(pathname);
        for(Message message : messages) {
            System.out.println(MessageFormat.format(message.getViberTxt(), message.getParams().toArray()));
            System.out.println(MessageFormat.format(message.getSmsTxt(), message.getParams().toArray()));
        }

    }

    public static List<Message> readMessagesFromExcelFile(String excelFilePath) throws IOException {
        List<Message> listMessages = new ArrayList<Message>();
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        //firstSheet.removeRowBreak();
        System.out.println(getLastNonEmptyRow(firstSheet));
        for (Row nextRow : firstSheet) {
            if(nextRow.getRowNum() == 0)
                continue;
            if(nextRow.getRowNum() > getLastNonEmptyRow(firstSheet))
                break;
            Message message = new Message();
            List<String> params = new ArrayList<String>();
            for (Cell nextCell : nextRow) {
                int columnIndex = nextCell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        message.setSubject(getCellValue(nextCell));
                        break;
                    case 1:
                        message.setStartTime(getCellValue(nextCell));
                        break;
                    case 2:
                        message.setViberTtl(getCellValue(nextCell));
                        break;
                    case 3:
                        message.setSmsTtl(getCellValue(nextCell));
                        break;
                    case 4:
                        message.setPriority(getCellValue(nextCell));
                        break;
                    case 5:
                        message.setImgUrl(getCellValue(nextCell));
                        break;
                    case 6:
                        message.setCaptionTxt(getCellValue(nextCell));
                        break;
                    case 7:
                        message.setCaptionUrl(getCellValue(nextCell));
                        break;
                    case 8:
                        message.setViberTxt(getCellValue(nextCell));
                        break;
                    case 9:
                        message.setSmsTxt(getCellValue(nextCell));
                        break;
                    case 10:
                        message.setContractCode(getCellValue(nextCell));
                        break;
                    case 11:
                        message.setPhoneNumber(getCellValue(nextCell));
                        break;
                    default:
                        params.add(getCellValue(nextCell));
                        break;
                }
            }
            message.setParams(params);
            listMessages.add(message);
        }

        workbook.close();
        inputStream.close();

        return listMessages;
    }

    private static String getCellValue(Cell cell) {
        DataFormatter df = new DataFormatter();
        if(cell.getCellTypeEnum()==NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            String withTime = "yyyy-MM-dd hh:mm:ss";
            String trunc = "dd.MM.yyyy";
            Date date = cell.getDateCellValue();
            if (new SimpleDateFormat("HH:mm:ss").format(date).equals("00:00:00")){

                return new SimpleDateFormat(trunc).format(date);
            }
            return new SimpleDateFormat(withTime).format(date);
        }
        return df.formatCellValue(cell);
    }

    private static int getLastNonEmptyRow(Sheet sheet){
        for (int rowNum = sheet.getLastRowNum(); rowNum >= 0; rowNum--) {
            final Row row = sheet.getRow(rowNum);
            if (row != null && row.getCell(0) != null && row.getCell(0).getCellTypeEnum() != BLANK) {
                return rowNum;
            }
        }
        return 0;
    }
}