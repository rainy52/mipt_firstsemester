package com.mipt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class LoggingDecoratorTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void saveDataTest() {
    DataService testDataService = new SimpleDataService();
    LoggingDecorator decorator = new LoggingDecorator(testDataService);

    decorator.saveData("key", "data");

    String output = outContent.toString();
    assertTrue(output.contains("CALL saveData with key=key, data=data"));
  }

  @Test
  void findDataByKeyNotNull() {
    DataService testDataService = new SimpleDataService();
    LoggingDecorator decorator = new LoggingDecorator(testDataService);

    decorator.saveData("key", "data");

    Optional<String> result = decorator.findDataByKey("key");

    assertTrue(result.isPresent());
    assertEquals("data", result.get());

    String output = outContent.toString();
    assertTrue(output.contains("CALL findDataByKey with key=key"));
    assertTrue(output.contains("RETURN findDataByKey with response=data"));
  }

  @Test
  void findDataByKeyNull() {
    DataService testDataService = new SimpleDataService();
    LoggingDecorator decorator = new LoggingDecorator(testDataService);

    Optional<String> result = decorator.findDataByKey("key");

    assertFalse(result.isPresent());
    String output = outContent.toString();
    assertTrue(output.contains("CALL findDataByKey with key=key"));
    assertTrue(output.contains("RETURN findDataByKey with response=NULL"));
  }

  @Test
  void deleteDataSuccess() {
    DataService testDataService = new SimpleDataService();
    LoggingDecorator decorator = new LoggingDecorator(testDataService);

    decorator.saveData("key", "data");

    boolean deleted = decorator.deleteData("key");

    assertTrue(deleted);
    String output = outContent.toString();
    assertTrue(output.contains("CALL deleteData with key=key"));
    assertTrue(output.contains("RETURN deleteData with response=true"));
  }

  @Test
  void deleteDataUnsuccess() {
    DataService testDataService = new SimpleDataService();
    LoggingDecorator decorator = new LoggingDecorator(testDataService);

    boolean deleted = decorator.deleteData("key");

    assertFalse(deleted);
    String output = outContent.toString();
    assertTrue(output.contains("CALL deleteData with key=key"));
    assertTrue(output.contains("RETURN deleteData with response=false"));
  }
}