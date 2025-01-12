import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        while (true) {
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();

            char[][] field = new char[7][7];
            String[][] displayField = new String[7][7];
            for (int i = 0; i < 7; i++) {
                Arrays.fill(field[i], ".");
                Arrays.fill(displayField[i], "🌊");
            }
        }
    }
}
