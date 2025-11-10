package com.mipt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

  @Test
  void testSplitAndMergeFile(@TempDir Path tempDir) throws IOException {
    FileProcessor processor = new FileProcessor();

    Path testFile = tempDir.resolve("test.dat");
    byte[] testData = new byte[1500];
    new SecureRandom().nextBytes(testData);
    Files.write(testFile, testData);

    Path partsDir = tempDir.resolve("parts");
    List<Path> parts = processor.splitFile(testFile.toString(), partsDir.toString(), 500);

    assertEquals(3, parts.size());

    for (Path part : parts) {
      assertTrue(Files.exists(part));
      assertEquals(500L, Files.size(part));
    }

    Path mergedFile = tempDir.resolve("merged.dat");
    processor.mergeFiles(parts, mergedFile.toString());

    byte[] originalBytes = Files.readAllBytes(testFile);
    byte[] mergedBytes = Files.readAllBytes(mergedFile);
    assertArrayEquals(originalBytes, mergedBytes);
  }
}