package com.mipt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachingDecorator implements DataService {
  private final DataService service;
  private final Map<String, String> cache = new HashMap<>();

  public CachingDecorator(DataService service) {
    this.service = service;
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    if (cache.containsKey(key)) {
      return Optional.of(cache.get(key));
    }
    Optional<String> res = service.findDataByKey(key);
    if (res.isPresent()) {
      cache.put(key, res.get());
    }
    return res;
  }

  @Override
  public void saveData(String key, String data) {
    service.saveData(key, data);
    cache.put(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    if (service.deleteData(key)) {
      cache.remove(key);
      return true;
    }
    return false;
  }
}
