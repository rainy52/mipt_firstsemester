package com.mipt;

import java.util.Optional;

public class ValidationDecorator implements DataService {
  private void validate(String data) {
    if (data == null || data.trim().isEmpty()) {
      throw new IllegalArgumentException("Argument cant be empty");
    }
  }

  private final DataService service;

  public ValidationDecorator(DataService service) {
    this.service = service;
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    validate(key);
    return service.findDataByKey(key);
  }

  @Override
  public void saveData(String key, String data) {
    validate(key);
    validate(data);
    service.saveData(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    validate(key);
    return service.deleteData(key);
  }
}
