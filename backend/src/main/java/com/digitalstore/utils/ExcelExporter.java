package com.digitalstore.utils;

import com.digitalstore.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

  public static ByteArrayInputStream productsToExcel(List<Product> products) throws IOException {
    String[] columns = {"ID", "Name", "Category", "Price", "Quantity"};
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      Sheet sheet = workbook.createSheet("Products");

      // Header row
      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < columns.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columns[i]);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        cell.setCellStyle(style);
      }

      int rowIdx = 1;
      for (Product product : products) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(product.getId());
        row.createCell(1).setCellValue(product.getName());
        row.createCell(2).setCellValue(product.getCategory());
        row.createCell(3).setCellValue(product.getPrice() != null ? product.getPrice() : 0);
        row.createCell(4).setCellValue(product.getQuantity() != null ? product.getQuantity() : 0);
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }
}
