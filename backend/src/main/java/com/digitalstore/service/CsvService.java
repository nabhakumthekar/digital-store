package com.digitalstore.service;

import com.digitalstore.model.Product;
import com.digitalstore.repository.ProductRepository;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CsvService {
  private final ProductRepository repo;

  public CsvService(ProductRepository repo) {
    this.repo = repo;
  }

  public String uploadProducts(MultipartFile file) throws IOException {
    int batchSize = 1000; // commit after every 1000 rows
    int totalCount = 0;
    List<Product> buffer = new ArrayList<>(batchSize);

    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

      String header = reader.readLine(); // skip header
      if (header == null) return "Empty CSV file";

      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        if (data.length < 4) continue;

        Product p = new Product();
        p.setName(data[0].trim());
        p.setCategory(data[1].trim());
        p.setPrice(Double.parseDouble(data[2].trim()));
        p.setQuantity(Integer.parseInt(data[3].trim()));
        buffer.add(p);
        totalCount++;

        if (buffer.size() >= batchSize) {
          repo.saveAll(buffer);
          buffer.clear();
        }
      }

      if (!buffer.isEmpty()) {
        repo.saveAll(buffer);
      }

      return "File processed successfully. Total records: " + totalCount;

    } catch (Exception e) {
      e.printStackTrace();
      return "Error: " + e.getMessage();
    }
  }
}
