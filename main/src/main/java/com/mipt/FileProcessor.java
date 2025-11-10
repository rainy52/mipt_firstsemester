package com.mipt;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

  public List<Path> splitFile(String sourcePath, String outputDir, int partSize) throws IOException {
    Path source = Paths.get(sourcePath);
    Path outputDirectory = Paths.get(outputDir);

    Files.createDirectories(outputDirectory);

    String fileName = source.getFileName().toString();
    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));

    List<Path> partPaths = new ArrayList<>();

    try (FileChannel sourceChannel = FileChannel.open(source)) {
      long fileSize = sourceChannel.size();
      long position = 0;
      int partNumber = 1;

      ByteBuffer buffer = ByteBuffer.allocate(partSize);

      while (position < fileSize) {
        int bytesToRead = (int) Math.min(partSize, fileSize - position);

        String partFileName = baseName + ".part" + partNumber;
        Path partPath = outputDirectory.resolve(partFileName);
        partPaths.add(partPath);

        try (FileChannel partChannel = FileChannel.open(
            partPath,
            StandardOpenOption.CREATE,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING)) {

          sourceChannel.position(position);
          int bytesRead = sourceChannel.read(buffer);
          if (bytesRead == -1) break;

          buffer.flip();

          partChannel.write(buffer);

          buffer.clear();
        }

        position += bytesToRead;
        partNumber++;
      }
    }

    return partPaths;
  }

  public void mergeFiles(List<Path> partPaths, String outputPath) throws IOException {
    for (Path part : partPaths) {
      if (!Files.exists(part)) {
        throw new IOException("Cannot access file part " + part);
      }
    }

    Path output = Paths.get(outputPath);
    Files.createDirectories(output.getParent());

    try (FileChannel outputChannel = FileChannel.open(
        output,
        StandardOpenOption.CREATE,
        StandardOpenOption.WRITE,
        StandardOpenOption.TRUNCATE_EXISTING)) {

      for (Path partPath : partPaths) {
        try (FileChannel partChannel = FileChannel.open(partPath, StandardOpenOption.READ)) {
          partChannel.transferTo(0, partChannel.size(), outputChannel);
        }
      }
    }
  }
}
