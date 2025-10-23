package com.digitalstore.service;

import com.digitalstore.model.Product;
import com.digitalstore.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ProductService {
  private final ProductRepository repo;

  public ProductService(ProductRepository repo) {
    this.repo = repo;
  }

  public Page<Product> getProducts(String category, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    if (category != null && !category.isEmpty()) {
      category = category.trim();
      return repo.findByCategoryContainingIgnoreCase(category, pageable);
    }
    return repo.findAll(pageable);
  }

  public ResponseEntity<byte[]> downLoadProducts(String category) throws IOException {
    Pageable pageable = PageRequest.of(0, 10_000); // chunked retrieval
    SXSSFWorkbook workbook = new SXSSFWorkbook(100); // keep 100 rows in memory
    Sheet sheet = workbook.createSheet("Products");

    // header
    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Name");
    header.createCell(2).setCellValue("Category");
    header.createCell(3).setCellValue("Price");
    header.createCell(2).setCellValue("Quantity");

    int rowNum = 1;
    Page<Product> page;

    do {
      if (category != null && !category.isEmpty()) {
        page = repo.findByCategoryContainingIgnoreCase(category, pageable);
      } else {
        page = repo.findAll(pageable);
      }
      for (Product p : page.getContent()) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(p.getId());
        row.createCell(1).setCellValue(p.getName());
        row.createCell(2).setCellValue(p.getCategory());
        row.createCell(3).setCellValue(p.getPrice());
        row.createCell(2).setCellValue(p.getQuantity());
      }
      pageable = pageable.next();
    } while (page.hasNext());

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.dispose(); // delete temp files

    return ResponseEntity.ok()
      .header("Content-Disposition", "attachment; filename=products.xlsx")
      .body(out.toByteArray());
  }

  public Product save(Product p) {
    return repo.save(p);
  }
}
