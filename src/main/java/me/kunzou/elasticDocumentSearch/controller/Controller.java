package me.kunzou.elasticDocumentSearch.controller;

import me.kunzou.elasticDocumentSearch.dto.Document;
import me.kunzou.elasticDocumentSearch.dto.SearchResult;
import me.kunzou.elasticDocumentSearch.service.ElasticSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Controller {

  private ElasticSearchService elasticSearchService;

  public Controller(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @PostMapping(value = "/upload")
  public ResponseEntity<Document> attachmentUpload(@RequestParam("file") MultipartFile file) throws Exception {
    if (file.isEmpty()) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    ;
    return ResponseEntity.ok().body(elasticSearchService.addDataByMap(file));
  }

  @GetMapping(value = "/search/{keyword}")
  public ResponseEntity<List<SearchResult>> search(@PathVariable("keyword") String keyword) throws IOException {
    return ResponseEntity.ok().body(elasticSearchService.searchData(keyword));
  }

  @GetMapping(value = "/documents")
  public ResponseEntity<List<Document>> getAllDocuments() throws IOException {
    return ResponseEntity.ok().body(elasticSearchService.getAllDocuments());
  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity deleteDocument(@PathVariable("id") String id) throws IOException {
    elasticSearchService.delete(id);
    return new ResponseEntity(HttpStatus.OK);
  }
}
