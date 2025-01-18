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
            int shots = 0;
            int hits = 0;
            int totalShipParts = 11;

            while (hits < totalShipParts) {
                displayBoard(playerBoard);
                System.out.print("Enter your shot (e.g., B3): ");
                String input = scanner.nextLine().toUpperCase();
                if (!isValidShot(input)) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                int row = input.charAt(0) - 'A';
                int col = input.charAt(1) - '1';

                if (!playerBoard[row][col].equals("🌊")) {
                    System.out.println("You have already shot here. Please try again.");
                    continue;
                }

                shots++;
                if (hiddenBoard[row][col].equals("🚢")) {
                    playerBoard[row][col] = "🔥";
                    hiddenBoard[row][col] = "X";
                    hits++;
                    System.out.println("Hit!");
                } 
                else {
                    playerBoard[row][col] = "❌";
                    System.out.println("Miss!");
                }
            System.out.println("Congratulations, " + playerName + "! You won in " + shots + " shots.");
            playerScores.add(shots);
    
            System.out.print("Do you want to play again? (yes/no): ");
            if (!scanner.nextLine().equalsIgnoreCase("yes")) break;
            }
            System.out.println("Final Scores:");
            List<Integer> sortedScores = new ArrayList<>(playerScores);
            Collections.sort(sortedScores);
            for (int i = 0; i < sortedScores.size(); i++) {
                int score = sortedScores.get(i);
                int index = playerScores.indexOf(score);
                System.out.println(playerNames.get(index) + ": " + score + " shots");
            }
            scanner.close();
        }
    }

    static void initializeBoards(String[][] hiddenBoard, String[][] playerBoard) {
        for (int i = 0; i < 7; i++) {
            Arrays.fill(hiddenBoard[i], "🌊");
            Arrays.fill(playerBoard[i], "🌊");
        }
    }
    static void displayBoard(String[][] board) {
        System.out.print("  1 2 3 4 5 6 7\n");
        for (int i = 0; i < 7; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    static boolean isValidShot(String input) {
        if (input.length() != 2) return false;
        char row = input.charAt(0);
        char col = input.charAt(1);
        return row >= 'A' && row <= 'G' && col >= '1' && col <= '7';
    }
    static void placeShips(String[][] board) {
        placeShip(board, 3);
        placeShip(board, 2);
        placeShip(board, 2);
        placeShip(board, 1);
        placeShip(board, 1);
        placeShip(board, 1);
        placeShip(board, 1);
    }
    static void placeShip(String[][] board, int size) {
        Random random = new Random();
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(7);
            int col = random.nextInt(7);
            boolean horizontal = random.nextBoolean();
        }
    }
}