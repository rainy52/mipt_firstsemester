package com.mipt;

import java.util.Optional;

public class LoggingDecorator implements DataService {
  private final DataService service;

  public LoggingDecorator(DataService service) {
    this.service = service;
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    System.out.println("CALL findDataByKey with key=" + key);
    Optional<String> res = service.findDataByKey(key);
    System.out.println("RETURN findDataByKey with response=" + res.orElse("NULL"));
    return res;
  }

  @Override
  public void saveData(String key, String data) {
    System.out.println("CALL saveData with key=" + key + ", " + "data=" + data);
    service.saveData(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    System.out.println("CALL deleteData with key=" + key);
    boolean res = service.deleteData(key);
    System.out.println("RETURN deleteData with response=" + res);
    return res;
  }
}
