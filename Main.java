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
            initializeBoards(hiddenBoard, playerBoard);

            placeShips(hiddenBoard);

            int shotsTaken = 0;
            int hitsCount = 0;
            int totalShipParts = 11;

            while (hitsCount < totalShipParts) {
                displayBoard(playerBoard);
                System.out.print("Enter your shot (e.g., B3): ");
                String shotInput = scanner.nextLine().toUpperCase();

                if (!isValidShot(shotInput)) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                int shotRow = shotInput.charAt(0) - 'A';
                int shotCol = shotInput.charAt(1) - '1';

                if (!playerBoard[shotRow][shotCol].equals("🌊")) {
                    System.out.println("You have already shot here. Please try again.");
                    continue;
                }

                shotsTaken++;
                if (hiddenBoard[shotRow][shotCol].equals("🚢")) {
                    playerBoard[shotRow][shotCol] = "🔥";
                    hiddenBoard[shotRow][shotCol] = "X";
                    hitsCount++;
                    System.out.println("Hit!");
                } else {
                    playerBoard[shotRow][shotCol] = "❌";
                    System.out.println("Miss!");
                }
            }

            System.out.println("Congratulations, " + playerName + "! You won in " + shotsTaken + " shots.");
            playerScores.add(shotsTaken);

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

    static void initializeBoards(String[][] hiddenBoard, String[][] playerBoard) {
        for (int row = 0; row < 7; row++) {
            Arrays.fill(hiddenBoard[row], "🌊");
            Arrays.fill(playerBoard[row], "🌊");
        }
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

    static void placeShip(String[][] board, int shipSize) {
        Random random = new Random();
        boolean shipPlaced = false;

        while (!shipPlaced) {
            int startRow = random.nextInt(7);
            int startCol = random.nextInt(7);
            boolean isHorizontal = random.nextBoolean();

            if (canPlaceShip(board, startRow, startCol, shipSize, isHorizontal)) {
                for (int part = 0; part < shipSize; part++) {
                    if (isHorizontal) {
                        board[startRow][startCol + part] = "🚢";
                    } else {
                        board[startRow + part][startCol] = "🚢";
                    }
                }
                shipPlaced = true;
            }
        }
    }

    static boolean canPlaceShip(String[][] board, int row, int col, int size, boolean horizontal) {
        for (int part = 0; part < size; part++) {
            int currentRow;
            int currentCol;

            if (horizontal) {
                currentRow = row;
                currentCol = col + part;
            } else {
                currentRow = row + part;
                currentCol = col;
            }

            if (currentRow < 0 || currentRow >= 7 || currentCol < 0 || currentCol >= 7 || !board[currentRow][currentCol].equals("🌊")) {
                return false;
            }

            for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                for (int colOffset = -1; colOffset <= 1; colOffset++) {
                    int adjacentRow = currentRow + rowOffset;
                    int adjacentCol = currentCol + colOffset;

                    if (adjacentRow >= 0 && adjacentRow < 7 && adjacentCol >= 0 && adjacentCol < 7 && board[adjacentRow][adjacentCol].equals("🚢")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static boolean isValidShot(String input) {
        if (input.length() != 2) return false;
        char row = input.charAt(0);
        char col = input.charAt(1);
        return row >= 'A' && row <= 'G' && col >= '1' && col <= '7';
    }

    static void displayBoard(String[][] board) {
        System.out.print("  1 2 3 4 5 6 7\n");
        for (int row = 0; row < 7; row++) {
            System.out.print((char) ('A' + row) + " ");
            for (int col = 0; col < 7; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}

