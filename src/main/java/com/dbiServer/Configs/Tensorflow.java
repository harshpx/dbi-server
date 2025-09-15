package com.dbiServer.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tensorflow.SavedModelBundle;

@Configuration
public class Tensorflow {
  @Bean(destroyMethod = "close")
  public SavedModelBundle loadModel() {
    return SavedModelBundle.load("models/dbi_model", "serve");
  }
}
