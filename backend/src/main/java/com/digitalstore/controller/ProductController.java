package com.digitalstore.controller;

import com.digitalstore.model.Product;
import com.digitalstore.service.CsvService;
import com.digitalstore.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

  private final CsvService csvService;
  private final ProductService productService;

  public ProductController(CsvService csvService, ProductService productService) {
    this.csvService = csvService;
    this.productService = productService;
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(
    @RequestParam("file") MultipartFile file) {
    try {
      String message = csvService.uploadProducts(file);
      return ResponseEntity.ok(message);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
    }
  }

  @GetMapping
  public Page<Product> listProducts(
    @RequestParam(required = false) String category,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    return productService.getProducts(category, page, size);
  }

  @GetMapping("/download")
  public ResponseEntity<byte[]> downloadProducts(@RequestParam(required = false) String category) {
    try {
      return productService.downLoadProducts(category);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
