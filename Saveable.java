import java.io.PrintStream;
import java.util.Scanner;

public interface Saveable {
    public void save (PrintStream printStream);
    public void load (Scanner sc);
}
