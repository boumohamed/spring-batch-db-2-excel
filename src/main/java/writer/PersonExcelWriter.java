package writer;

import dto.Person;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemWriter;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PersonExcelWriter implements ItemWriter<Person> {

    private static final String FILE_PATH = "output.xlsx";

    @Override
    public void write(List<? extends Person> items) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persons");

        // Create header
        createHeader(sheet);

        int rowCount = 1;

        for (Person person : items) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(person.getId());
            row.createCell(1).setCellValue(person.getFirstName());
            row.createCell(2).setCellValue(person.getLastName());
            row.createCell(3).setCellValue(person.getEmail());
        }

        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    private void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("First Name");
        header.createCell(2).setCellValue("Last Name");
        header.createCell(3).setCellValue("Email");
    }
}