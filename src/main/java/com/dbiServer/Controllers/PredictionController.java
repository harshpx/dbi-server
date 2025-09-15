package com.dbiServer.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbiServer.DTOs.CommonResponse;
import com.dbiServer.DTOs.PredictionResult;
import com.dbiServer.DTOs.URLRequest;
import com.dbiServer.Services.PredictionService;

@RestController
@RequestMapping("predict")
public class PredictionController {
  @Autowired
  private PredictionService service;

  @GetMapping("test")
  public ResponseEntity<CommonResponse<String>> testModel() {
    return ResponseEntity.ok(new CommonResponse<>(service.checkModel()));
  }

  @PostMapping("url")
  public ResponseEntity<CommonResponse<PredictionResult>> predictFromUrl(@RequestBody URLRequest request)
      throws IOException {
    PredictionResult result = service.predictFromUrl(request.url);
    return ResponseEntity.ok(new CommonResponse<>(result));
  }

  @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CommonResponse<PredictionResult>> predictFromUpload(@RequestParam("file") MultipartFile file)
      throws IOException {
    PredictionResult result = service.predictFromFile(file);
    return ResponseEntity.ok(new CommonResponse<>(result));
  }

  @PostMapping("preview")
  public ResponseEntity<byte[]> getProcessedImage(@RequestBody URLRequest request)
      throws IOException {
    byte[] imgBytes = service.getProcessedImage(request.url);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imgBytes);
  }
}
