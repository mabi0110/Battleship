import java.util.Arrays;
import java.util.Objects;

public class Field {

    private final int[] FIRST_ROW = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final char[] FIRST_COLUMN = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private final int SIZE = 10;
    private final String[][] GAME_FIELD = new String[SIZE][SIZE];

    Field() {
        createGameField();
    }

    public void print() {
        System.out.println();
        printFirstRow();
        printFirstColumnAndGameField();
    }

    private void createGameField() {
        for (String[] strings : GAME_FIELD) {
            Arrays.fill(strings, "~");
        }
    }

    private void printFirstColumnAndGameField() {
        for (int i = 0; i < GAME_FIELD.length; i++) {
            System.out.print(FIRST_COLUMN[i] + " ");
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                System.out.print(GAME_FIELD[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printFirstRow() {
        System.out.print("  ");
        for (int i : FIRST_ROW) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void updateGameField(char letter, int number) {
        int index = findIndexBasedOnLetter(letter);

        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i == index && j == number - 1) {
                    if (Objects.equals(GAME_FIELD[i][j], "O")) {
                        GAME_FIELD[i][j] = "X";
                        print();
                        System.out.println("\n" + "You hit a ship!");
                    } else if (Objects.equals(GAME_FIELD[i][j], "~")) {
                        GAME_FIELD[i][j] = "M";
                        print();
                        System.out.println("\n" + "You missed!");
                    }
                }
            }
        }
    }

    public void placeAShip(char[] letters, int[] numbers) {
        if (shipIsHorizontal(letters, numbers)) {
            placeAShipHorizontally(letters, numbers);
        } else if (shipIsVertical(letters, numbers)) {
            placeAShipVertically(letters, numbers);
        }
    }

    private void placeAShipVertically(char[] letters, int[] numbers) {
        int start = findIndexBasedOnLetter((char) Math.min(letters[0], letters[1]));
        int stop = findIndexBasedOnLetter((char) Math.max(letters[0], letters[1]));
        int index = numbers[0] - 1;

        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i >= start && i <= stop && j == index) {
                    GAME_FIELD[i][j] = "O";
                }
            }
        }
    }

    private void placeAShipHorizontally(char[] letters, int[] numbers) {
        int start = Math.min(numbers[0], numbers[1]) - 1;
        int stop = Math.max(numbers[0], numbers[1]) - 1;
        int index = findIndexBasedOnLetter(letters[0]);

        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i == index && j >= start && j <= stop) {
                    GAME_FIELD[i][j] = "O";
                }
            }
        }
    }

    private int findIndexBasedOnLetter(char letter) {
        int index = -1;
        for (int i = 0; i < FIRST_COLUMN.length; i++) {
            if (FIRST_COLUMN[i] == letter) {
                index = i;
            }
        }
        return index;
    }

    public void checkIfCoordinatesAreCorrect(char[] letters, int[] numbers, EnumShip ship)
            throws WrongShipLocationException, WrongLengthOfShipException, TooCloseToAnotherOneException {

        if (!shipLocationIsOk(letters, numbers)) {
            throw new WrongShipLocationException("\n" + "Error! Wrong ship location! Try again: ");
        }

        if (shipIsHorizontal(letters, numbers)) {
            if (!horizontalLengthIsOk(numbers, ship)) {
                throw new WrongLengthOfShipException("\n" + "Error! Wrong length of the " + ship.getName() + "! Try again:");
            }
            if (!(checkLowerRow(letters, numbers) && checkUpperRow(letters, numbers)
                    && checkRightCell(letters, numbers) && checkLeftCell(letters, numbers))) {
                throw new TooCloseToAnotherOneException("\n" + "\"Error! You placed it too close to another one. Try again:");
            }
        } else if (shipIsVertical(letters, numbers)) {
            if (!VerticalLengthIsOk(letters, ship)) {
                throw new WrongLengthOfShipException("\n" + "Error! Wrong length of the " + ship.getName() + "! Try again:");
            }
            if (!(checkLeftColumn(letters, numbers) && checkRightColumn(letters, numbers)
                    && checkUpperCell(letters, numbers) && checkLowerCell(letters, numbers))) {
                throw new TooCloseToAnotherOneException("\n" + "\"Error! You placed it too close to another one. Try again:");
            }
        }
    }

    private boolean VerticalLengthIsOk(char[] letters, EnumShip ship) {
        return ship.getCells() == Math.abs(letters[1] - letters[0]) + 1;
    }

    private boolean shipIsVertical(char[] letters, int[] numbers) {
        return letters[0] != letters[1] && numbers[0] == numbers[1];
    }

    private boolean horizontalLengthIsOk(int[] numbers, EnumShip ship) {
        return ship.getCells() == Math.abs(numbers[1] - numbers[0]) + 1;
    }

    private boolean shipIsHorizontal(char[] letters, int[] numbers) {
        return letters[0] == letters[1] && numbers[0] != numbers[1];
    }

    private boolean shipLocationIsOk(char[] letters, int[] numbers) {
        return !(letters[0] != letters[1] && numbers[0] != numbers[1]);
    }


    private boolean isCellEmpty(int letterIndex, int numberIndex) {
        boolean isCellEmpty = true;
        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i == letterIndex && j == numberIndex) {
                    if (Objects.equals(GAME_FIELD[i][j], "O")) {
                        isCellEmpty = false;
                    }
                }
            }
        }
        return isCellEmpty;
    }

    private boolean checkUpperCell(char[] letters, int[] numbers) {
        int letterIndex = findIndexBasedOnLetter((char) Math.min(letters[0], letters[1]));
        int numberIndex = numbers[0] - 1;
        return isCellEmpty(letterIndex - 1, numberIndex);
    }

    private boolean checkLowerCell(char[] letters, int[] numbers) {
        int letterIndex = findIndexBasedOnLetter((char) Math.max(letters[0], letters[1]));
        int numberIndex = numbers[0] - 1;
        return isCellEmpty(letterIndex + 1, numberIndex);
    }

    private boolean checkLeftCell(char[] letters, int[] numbers) {
        int letterIndex = findIndexBasedOnLetter(letters[0]);
        int numberIndex = Math.min(numbers[0], numbers[1]) - 1;
        return isCellEmpty(letterIndex, numberIndex - 1);
    }

    private boolean checkRightCell(char[] letters, int[] numbers) {
        int letterIndex = findIndexBasedOnLetter(letters[0]);
        int numberIndex = Math.max(numbers[0], numbers[1]) - 1;
        return isCellEmpty(letterIndex, numberIndex + 1);
    }

    private boolean isColumnEmpty(int start, int stop, int index) {
        boolean isColumnEmpty = true;
        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i >= start && i <= stop && j == index) {
                    if (Objects.equals(GAME_FIELD[i][j], "O")) {
                        isColumnEmpty = false;
                    }
                }
            }
        }
        return isColumnEmpty;
    }

    private boolean checkLeftColumn(char[] letters, int[] numbers) {
        int letterStart = findIndexBasedOnLetter((char) Math.min(letters[0], letters[1]));
        int letterStop = findIndexBasedOnLetter((char) Math.max(letters[0], letters[1]));
        int numberIndex = (numbers[0]) - 1;
        return isColumnEmpty(letterStart, letterStop, numberIndex - 1);
    }

    private boolean checkRightColumn(char[] letters, int[] numbers) {
        int letterStart = findIndexBasedOnLetter((char) Math.min(letters[0], letters[1]));
        int letterStop = findIndexBasedOnLetter((char) Math.max(letters[0], letters[1]));
        int numberIndex = (numbers[0]) - 1;
        return isColumnEmpty(letterStart, letterStop, numberIndex + 1);
    }

    private boolean isRowEmpty(int start, int stop, int index) {
        boolean isRowEmpty = true;
        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i == index && j >= start && j <= stop) {
                    if (Objects.equals(GAME_FIELD[i][j], "O")) {
                        isRowEmpty = false;
                    }
                }
            }
        }
        return isRowEmpty;
    }

    private boolean checkLowerRow(char[] letters, int[] numbers) {
        int numberStart = Math.min(numbers[0], numbers[1]) - 1;
        int numberStop = Math.max(numbers[0], numbers[1]) - 1;
        int letterIndex = findIndexBasedOnLetter(letters[0]);
        return isRowEmpty(numberStart, numberStop, letterIndex + 1);
    }

    private boolean checkUpperRow(char[] letters, int[] numbers) {
        int numberStart = Math.min(numbers[0], numbers[1]) - 1;
        int numberStop = Math.max(numbers[0], numbers[1]) - 1;
        int letterIndex = findIndexBasedOnLetter(letters[0]);
        return isRowEmpty(numberStart, numberStop, letterIndex - 1);
    }

    public void checkIfShotCoordinatesAreCorrect(char firstCoordinate, int secondCoordinate) throws EnteredWrongCoordinatesException {
        if (firstCoordinate < 'A' || firstCoordinate > 'J'
                || secondCoordinate < 1 || secondCoordinate > 10) {
            throw new EnteredWrongCoordinatesException("\n" +
                    "Error! You entered the wrong coordinates! Try again:");
        }
    }
}
