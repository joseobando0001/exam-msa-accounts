package com.pichincha.exam.accounts.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pichincha.exam.models.MovementFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;

import static com.pichincha.exam.accounts.constants.Constants.*;

public class Util {
    private Util() {

    }

    public static Mono<byte[]> generatePdfReport(Flux<MovementFilter> movements) {
        return movements.collectList()
                .flatMap(movementList -> Mono.fromCallable(() -> {
                    Document document = new Document(PageSize.A4, 20, 20, 20, 20);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    PdfWriter.getInstance(document, outputStream);
                    document.open();

                    PdfPTable table = new PdfPTable(9);
                    table.addCell(DATE);
                    table.addCell(CLIENT);
                    table.addCell(ACCOUNT_NUMBER);
                    table.addCell(TYPE);
                    table.addCell(INITIAL_BALANCE);
                    table.addCell(STATUS);
                    table.addCell(AMOUNT);
                    table.addCell(TYPE_MOVEMENT);
                    table.addCell(BALANCE_AVAILABLE);
                    Font dataFont = new Font(Font.FontFamily.HELVETICA, 9);

                    for (MovementFilter movement : movementList) {
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getDate()), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(movement.getClient(), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(movement.getAccountNumber(), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getType()), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getInitBalance()), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getStatus()), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getValue()), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getTypeMovement()), dataFont)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(movement.getAvailableBalance()), dataFont)));

                    }

                    document.add(table);
                    document.close();

                    return outputStream.toByteArray();
                }));
    }


    public static Mono<byte[]> generateExcelReport(Flux<MovementFilter> movements) {
        return movements.collectList()
                .flatMap(movementList -> Mono.fromCallable(() -> {
                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet(SHEET_NAME);

                    Row headerRow = sheet.createRow(0);
                    String[] headers = {DATE, CLIENT, ACCOUNT_NUMBER, TYPE, INITIAL_BALANCE, STATUS, AMOUNT, TYPE_MOVEMENT, BALANCE_AVAILABLE};
                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                    }

                    int rowNum = 1;
                    for (MovementFilter movement : movementList) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(String.valueOf(movement.getDate()));
                        row.createCell(1).setCellValue(movement.getClient());
                        row.createCell(2).setCellValue(movement.getAccountNumber());
                        row.createCell(3).setCellValue(String.valueOf(movement.getType()));
                        row.createCell(4).setCellValue(String.valueOf(movement.getInitBalance()));
                        row.createCell(5).setCellValue(String.valueOf(movement.getStatus()));
                        row.createCell(6).setCellValue(String.valueOf(movement.getValue()));
                        row.createCell(7).setCellValue(String.valueOf(movement.getTypeMovement()));
                        row.createCell(8).setCellValue(String.valueOf(movement.getAvailableBalance()));
                    }

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    workbook.write(outputStream);
                    workbook.close();

                    return outputStream.toByteArray();
                }));
    }

}
