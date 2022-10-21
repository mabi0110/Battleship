import exceptions.EnteredWrongCoordinatesException;
import exceptions.TooCloseToAnotherOneException;
import exceptions.WrongLengthOfShipException;
import exceptions.WrongShipLocationException;

import java.util.Arrays;
import java.util.Objects;

public class Field {

    private final int[] FIRST_ROW = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final char[] FIRST_COLUMN = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private final int SIZE = 10;
    private final String[][] GAME_FIELD = new String[SIZE][SIZE];
    private int[] shipsLength = {
            Ship.AIRCRAFT_CARRIER.getCells(),
            Ship.BATTLESHIP.getCells(),
            Ship.SUBMARINE.getCells(),
            Ship.CRUISER.getCells(),
            Ship.DESTROYER.getCells()
    };
    public int[] getShipsLength() {
        return shipsLength;
    }

    public void setShipsLength(int[] shipsLength) {
        this.shipsLength = shipsLength;
    }
    private int index = 0;
    private String[] correctShots = new String[index];

    Field() {
        createGameField();
    }

    public void print() {
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
        System.out.print("\n  ");
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
                    } else if (Objects.equals(GAME_FIELD[i][j], "~")) {
                        GAME_FIELD[i][j] = "M";
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

    public void checkIfCoordinatesAreCorrect(char[] letters, int[] numbers, Ship ship)
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

    private boolean VerticalLengthIsOk(char[] letters, Ship ship) {
        return ship.getCells() == Math.abs(letters[1] - letters[0]) + 1;
    }

    private boolean shipIsVertical(char[] letters, int[] numbers) {
        return letters[0] != letters[1] && numbers[0] == numbers[1];
    }

    private boolean horizontalLengthIsOk(int[] numbers, Ship ship) {
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

    public void checkIfShotCoordinatesAreCorrect(char firstCoordinate, int secondCoordinate)
            throws EnteredWrongCoordinatesException {
        if (firstCoordinate < 'A' || firstCoordinate > 'J'
                || secondCoordinate < 1 || secondCoordinate > 10) {
            throw new EnteredWrongCoordinatesException("\n" +
                    "Error! You entered the wrong coordinates! Try again:");
        }
    }
    private void updateArray(int k) {
        String wonMessage = "\nYou sank the last ship. You won. Congratulations!";
        String sankShipMessage = "\nYou sank a ship!";
        String hitShipMessage = "\nYou hit a ship! ";
        int[] shipsLength = getShipsLength();
        if (shipsLength[k] > 0) {
            shipsLength[k] -= 1;
            setShipsLength(shipsLength);
        }
        if (allShipsAreSank()){
            System.out.println(wonMessage);
            System.exit(0);
        }
        if (shipsLength[k] == 0) {
            System.out.println(sankShipMessage);
        }
        if (shipsLength[k] != 0) {
            System.out.println(hitShipMessage);
        }
    }

    private void updateShipsLength(String[][] shipsArray, char letter, int number) {
        String coordinate = "" + letter + number;
        for (int k = 0; k < shipsArray.length; k++) {
            for (int l = 0; l < shipsArray[k].length; l++) {
                if (shipsArray[k][l].equals(coordinate)) {
                    switch (k) {
                        case 0 -> updateArray(0);
                        case 1 -> updateArray(1);
                        case 2 -> updateArray(2);
                        case 3 -> updateArray(3);
                        case 4 -> updateArray(4);
                    }
                }
            }
        }
    }

    private boolean allShipsAreSank() {
        return shipsLength[0] == 0 && shipsLength[1] == 0 && shipsLength[2] == 0
                && shipsLength[3] == 0 && shipsLength[4] == 0;
    }

    public int placeAShot(Field gameField, char letter, int number, String[][] shipsArray) {
        int correctShot = 0;
        int letterIndex = findIndexBasedOnLetter(letter);
        int numberIndex = number - 1;

        String coordinate = "" + letter + number;

        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i == letterIndex && j == numberIndex) {
                    if (gameField.isCellEqual(letterIndex, numberIndex, "X")) {
                        GAME_FIELD[i][j] = "X";
                        print();
                        correctShots = extendArray(correctShots);
                        if (!checkDuplicates(correctShots, coordinate)) {
                            correctShot = 1;
                            updateShipsLength(shipsArray, letter, number);
                            correctShots[index] = coordinate;
                            index++;
                        } else {
                            System.out.println("\nYou hit a ship!");
                        }
                    }
                    if (gameField.isCellEqual(letterIndex, numberIndex, "M")) {
                        GAME_FIELD[i][j] = "M";
                        print();
                        System.out.println("\nYou missed!");
                    }
                }
            }
        }
        return correctShot;
    }

    private String[] extendArray(String[] correctShots) {
        String[] temp = new String[index + 1];
        System.arraycopy(correctShots, 0, temp, 0, correctShots.length);
        return temp;
    }


    private boolean checkDuplicates(String[] correctShots, String coordinate) {
        boolean isCoordinateInArray = false;
        for (int k = 0; k < index; k++) {
            if (correctShots[k].equals(coordinate)) {
                isCoordinateInArray = true;
            }
        }
        return isCoordinateInArray;
    }


    private boolean isCellEqual(int letterIndex, int numberIndex, String x) {
        boolean isCellEqual = false;
        for (int i = 0; i < GAME_FIELD.length; i++) {
            for (int j = 0; j < GAME_FIELD[i].length; j++) {
                if (i == letterIndex && j == numberIndex) {
                    if (Objects.equals(GAME_FIELD[i][j], x)) {
                        isCellEqual = true;
                    }
                }
            }
        }
        return isCellEqual;
    }

    public String[] createShipArray(char[] letters, int[] numbers, Ship ship) {
        int numberIndex = Math.min(numbers[0], numbers[1]);
        int letterIndex = ((char) Math.min(letters[0], letters[1]));

        String[] shipArray = new String[ship.getCells()];
        if (shipIsHorizontal(letters, numbers)) {
            for (int i = 0; i < shipArray.length; i++) {
                shipArray[i] = String.valueOf(letters[0]) + (numberIndex + i);
            }
        }
        if (shipIsVertical(letters, numbers)) {
            for (int i = 0; i < shipArray.length; i++) {
                shipArray[i] = (String.valueOf((char) (letterIndex + i))) + numbers[0];
            }
        }
        return shipArray;
    }
}
