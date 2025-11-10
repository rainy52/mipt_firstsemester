package com.mipt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import com.mipt.TextFileAnalyzer.AnalysisResult;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TextFileAnalyzerTest {

  @Test
  void testAnalyzeFile(@TempDir Path tempDir) throws IOException {
    TextFileAnalyzer analyzer = new TextFileAnalyzer();

    Path testFile = tempDir.resolve("input.txt");
    Files.write(testFile, List.of("Hello world!", "This is test."));

    AnalysisResult result = analyzer.analyzeFile(testFile.toString());

    assertEquals(2, result.getLineCount());
    assertEquals(5, result.getWordCount());
    assertEquals(25, result.getCharCount());

    Map<Character, Long> freq = result.getCharFrequency();
    assertEquals(3L, freq.get('l'));
    assertEquals(1L, freq.get('H'));
    assertTrue(freq.containsKey(' '));
  }

  @Test
  void testSaveAnalysisResult(@TempDir Path tempDir) throws IOException {
    TextFileAnalyzer analyzer = new TextFileAnalyzer();

    Map<Character, Long> freq = new HashMap<>();
    freq.put('a', 3L);
    freq.put('b', 1L);
    AnalysisResult result = new AnalysisResult(2, 5, 20, freq);

    Path outputFile = tempDir.resolve("analysis_result.txt");
    analyzer.saveAnalysisResult(result, outputFile.toString());

    assertTrue(Files.exists(outputFile));
    assertTrue(Files.size(outputFile) > 0);

    String content = Files.readString(outputFile);
    assertTrue(content.contains("Lines: 2"));
    assertTrue(content.contains("Words: 5"));
    assertTrue(content.contains("Chars: 20"));
    assertTrue(content.contains("'a': 3"));
  }
}
