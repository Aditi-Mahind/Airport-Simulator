import java.io.*;
import java.util.*;

class FileIO {
  private final FileWriter csvWriter;

  FileIO(FileWriter fileWriter, String[] fileHeaders) {
    this.csvWriter = fileWriter;
    try {
      for(String header : fileHeaders) {
        this.csvWriter.append(header);
        this.csvWriter.append(",");
      }
      this.csvWriter.append("\n");
    }
    catch(IOException e) {}
  }

  public void write(String[] row) {
    try {
      this.csvWriter.append(String.join(",", row));
      this.csvWriter.append("\n");
    }
    catch(Exception e) {
    }

  }

  void close() {
    try {
      this.csvWriter.flush();
      this.csvWriter.close();
    }
    catch(Exception e) {}
  }
}
