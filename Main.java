import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> playerNames = new ArrayList<>();
        List<Integer> playerScores = new ArrayList<>();

        while (true) {
            System.out.println("Welcome to Battleship!");
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();
            playerNames.add(playerName);
            String[][] hiddenBoard = new String[7][7];
            String[][] playerBoard = new String[7][7];
            initializeBoards(hiddenBoard,playerBoard);


        }
    }
    static void initializeBoards(String[][] hiddenBoard, String[][] playerBoard) {
        for (int i = 0; i < 7; i++) {
            Arrays.fill(hiddenBoard[i], "🌊");
            Arrays.fill(playerBoard[i], "🌊");
        }
    }
}