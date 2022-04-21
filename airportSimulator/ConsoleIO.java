import java.util.Scanner;
import java.io.PrintStream;

class ConsoleIO implements IO {
  private final Scanner inputDriver;
  private final PrintStream outputDriver;

  ConsoleIO() {
    this.inputDriver = new Scanner(System.in);
    this.outputDriver = System.out;
  }

  public void write(Object text) {
    this.outputDriver.println(text.toString());
  }

  public String read() {
    return this.inputDriver.nextLine();
  }
}
