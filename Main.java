import java.util.*;

public class Main {
    private final static int BOARD_SIZE = 7;
    private final static String EMPTY_CELL = "🌊";
    private final static String SHIP_CELL = "🚢";
    private final static String HIT_CELL = "🔥";
    private final static String MISS_CELL = "❌";
    private final static String SUNK_CELL = "⚓";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> playerNames = new ArrayList<>();
        List<Integer> playerScores = new ArrayList<>();

        while (true) {
            System.out.println("Welcome to Battleship!");
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();
            playerNames.add(playerName);

            String[][] hiddenBoard = new String[BOARD_SIZE][BOARD_SIZE];
            String[][] playerBoard = new String[BOARD_SIZE][BOARD_SIZE];
            initializeBoards(hiddenBoard, playerBoard);

            placeShips(hiddenBoard);

            int shotsTaken = 0;
            int hitsCount = 0;
            int totalShipParts = 11;

            while (hitsCount < totalShipParts) {
                displayBoard(playerBoard);
                System.out.print("Enter your shot (e.g., B3): ");
                String shotInput = scanner.nextLine().toUpperCase();
                clearScreen();

                if (!isValidShot(shotInput)) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                int shotRow = shotInput.charAt(0) - 'A';
                int shotCol = shotInput.charAt(1) - '1';
                boolean turnToHit = hiddenBoard[shotRow][shotCol].equals(SHIP_CELL);

                if (!playerBoard[shotRow][shotCol].equals(EMPTY_CELL)) {
                    System.out.println("You have already shot here. Please try again.");
                    continue;
                }

                shotsTaken++;
                if (turnToHit) {
                    playerBoard[shotRow][shotCol] = HIT_CELL;
                    hiddenBoard[shotRow][shotCol] = "X";
                    hitsCount++;
                    System.out.println("Hit!");
                    if (isShipSunk(hiddenBoard, playerBoard)) {
                        System.out.println("You sunk a ship!");
                    }
                } else {
                    playerBoard[shotRow][shotCol] = MISS_CELL;
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
        for (int score : sortedScores) {
            int index = playerScores.indexOf(score);
            System.out.println(playerNames.get(index) + ": " + score + " shots");
        }

        scanner.close();
    }

    static void initializeBoards(String[][] hiddenBoard, String[][] playerBoard) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            Arrays.fill(hiddenBoard[row], EMPTY_CELL);
            Arrays.fill(playerBoard[row], EMPTY_CELL);
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
            int startRow = random.nextInt(BOARD_SIZE);
            int startCol = random.nextInt(BOARD_SIZE);
            boolean isHorizontal = random.nextBoolean();

            if (canPlaceShip(board, startRow, startCol, shipSize, isHorizontal)) {
                for (int part = 0; part < shipSize; part++) {
                    if (isHorizontal) {
                        board[startRow][startCol + part] = SHIP_CELL;
                    } else {
                        board[startRow + part][startCol] = SHIP_CELL;
                    }
                }
                shipPlaced = true;
            }
        }
    }

    static boolean canPlaceShip(String[][] board, int row, int col, int size, boolean horizontal) {
        for (int part = 0; part < size; part++) {
            int currentRow = horizontal ? row : row + part;
            int currentCol = horizontal ? col + part : col;
            boolean placeCondition1 = currentRow < 0 || currentRow >= BOARD_SIZE || currentCol < 0 || currentCol >= BOARD_SIZE || !board[currentRow][currentCol].equals(EMPTY_CELL);
            if (placeCondition1) {
                return false;
            }

            for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                for (int colOffset = -1; colOffset <= 1; colOffset++) {
                    int adjacentRow = currentRow + rowOffset;
                    int adjacentCol = currentCol + colOffset;
                    boolean placeCondition2 = adjacentRow >= 0 && adjacentRow < BOARD_SIZE && adjacentCol >= 0 && adjacentCol < BOARD_SIZE && board[adjacentRow][adjacentCol].equals(SHIP_CELL);

                    if (placeCondition2) {
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
        System.out.print("   1  2  3  4  5  6  7\n");
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((char) ('A' + row) + " ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    static boolean isShipSunk(String[][] hiddenBoard, String[][] playerBoard) {
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boolean sunkCondition1 = hiddenBoard[row][col].equals("X") && !visited[row][col];
                if (sunkCondition1) {
                    List<int[]> shipParts = new ArrayList<>();
                    sunkShip(hiddenBoard, row, col, visited, shipParts);
                    boolean allHit = true;
                    for (int[] part : shipParts) {
                        boolean turnToSunk = !hiddenBoard[part[0]][part[1]].equals("X");
                        if (turnToSunk) {
                            allHit = false;
                            break;
                        }
                    }

                    if (allHit) {
                        for (int[] part : shipParts) {
                            playerBoard[part[0]][part[1]] = SUNK_CELL;
                            hiddenBoard[part[0]][part[1]] = SUNK_CELL;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static void sunkShip(String[][] board, int row, int col, boolean[][] visited, List<int[]> shipParts) {
        boolean check = row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || visited[row][col] || (!board[row][col].equals(SHIP_CELL) && !board[row][col].equals("X"));
        if (check) {
            return;
        }

        visited[row][col] = true;
        shipParts.add(new int[]{row, col});

        sunkShip(board, row - 1, col, visited, shipParts);
        sunkShip(board, row + 1, col, visited, shipParts);
        sunkShip(board, row, col - 1, visited, shipParts);
        sunkShip(board, row, col + 1, visited, shipParts);
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}