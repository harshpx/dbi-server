package com.dbiServer.Configs;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tensorflow.SavedModelBundle;

@Configuration
public class Tensorflow {
  @Bean(destroyMethod = "close")
  public SavedModelBundle loadModel() {
    String modelPath = Optional.ofNullable(System.getenv("MODEL_PATH"))
        .orElse("models/dbi_model");
    return SavedModelBundle.load(modelPath, "serve");
  }
}
