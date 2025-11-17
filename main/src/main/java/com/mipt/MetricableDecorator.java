package com.mipt;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class MetricableDecorator implements DataService {
  private final DataService service;

  public MetricableDecorator(DataService service) {
    this.service = service;
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    Instant startTime = Instant.now();
    Optional<String> res = service.findDataByKey(key);
    MetricService.sendMetric(Duration.between(startTime, Instant.now()));
    return res;
  }

  @Override
  public void saveData(String key, String data) {
    Instant startTime = Instant.now();
    service.saveData(key, data);
    MetricService.sendMetric(Duration.between(startTime, Instant.now()));
  }

  @Override
  public boolean deleteData(String key) {
    Instant startTime = Instant.now();
    boolean res = service.deleteData(key);
    MetricService.sendMetric(Duration.between(startTime, Instant.now()));
    return res;
  }

  public static class MetricService {
    public static void sendMetric(Duration duration) {
      System.out.println("Метод выполнялся: " + duration.toString());
    }
  }
}
