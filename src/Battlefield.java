import java.util.Objects;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Ship[] ships = Ship.values();


        Player[] players = {new Player("Player 1"), new Player("Player 2")};
        Field gameField1 = new Field();
        System.out.println(players[0].getName() + ", place your ships on the game field");
        Field fogOfWarField1 = new Field();
        String[][] shipsArray1 = new String[5][];
        int index1 = 0;
        takePosition(gameField1, ships, shipsArray1, index1);

        pressEnterToSwitchThePlayer();

//        Field gameField2 = new Field();
//        System.out.println(players[1].getName() + ", place your ships on the game field");
//        Field fogOfWarField2 = new Field();
//        String[][] shipsArray2 = new String[5][];
//        int index = 0;
//        takePosition(gameField2, ships, shipsArray2, index);


//        theFirstShot(gameField1, fogOfWarField1, shipsArray1);
//        theFirstShot(gameField2, fogOfWarField2, shipsArray2);


//        Ship[] ships = Ship.values();
//        Field gameField = new Field();
//        Field fogOfWarField = new Field();
//        String[][] shipsArray = new String[5][];
//        int index = 0;
//        takePosition(gameField, ships, shipsArray, index);
//        theFirstShot(gameField, fogOfWarField, shipsArray);
    }

    private static void theFirstShot(Field gameField, Field fogOfWarField, String[][] shipsArray) {
        int allShipsLength = 17;
        System.out.println("\n" + "The game starts!");
        fogOfWarField.print();
        System.out.println("\n" + "Take a shot!");

        char letter;
        int number;
        String coordinates;

        boolean wrongCoordinatesError = true;
        while (allShipsLength > 0) {
            do {
                try {
                    System.out.print("\n" + "> ");
                    coordinates = enterCoordinatesForShot();
                    letter = getLetterCoordinate(coordinates);
                    number = getNumberCoordinate(coordinates);
                    gameField.checkIfShotCoordinatesAreCorrect(letter, number);
                    wrongCoordinatesError = false;
                    gameField.updateGameField(letter, number);
                    int correctShot = fogOfWarField.placeAShot(gameField, letter, number, shipsArray);
                    if (correctShot == 1) {
                        allShipsLength--;
                    }
                } catch (EnteredWrongCoordinatesException e) {
                    System.out.println(e.getMessage());
                }
            } while (wrongCoordinatesError);
        }
    }

    private static void takePosition(Field gameField, Ship[] ships, String[][] shipsArray, int index) {
        gameField.print();
        char letter1, letter2;
        int number1, number2;
        String coordinates1, coordinates2;
        char[] letters = new char[2];
        int[] numbers = new int[2];

        for (Ship ship : ships) {
            System.out.println("\n" + "Enter the coordinates of the " + ship.getName() + " (" + ship.getCells() + " cells):");

            boolean error = true;
            do {
                System.out.print("\n" + "> ");
                String[] coordinates = enterCoordinatesForShip();

                coordinates1 = coordinates[0];
                coordinates2 = coordinates[1];

                letter1 = getLetterCoordinate(coordinates1);
                number1 = getNumberCoordinate(coordinates1);
                letter2 = getLetterCoordinate(coordinates2);
                number2 = getNumberCoordinate(coordinates2);

                letters[0] = letter1;
                letters[1] = letter2;

                numbers[0] = number1;
                numbers[1] = number2;

                try {
                    gameField.checkIfCoordinatesAreCorrect(letters, numbers, ship);
                    String[] shipArray = gameField.createShipArray(letters, numbers, ship);
                    shipsArray[index] = shipArray;
                    index++;
                    error = false;
                } catch (WrongShipLocationException | WrongLengthOfShipException | TooCloseToAnotherOneException e) {
                    System.out.println(e.getMessage());
                }
            } while (error);

            gameField.placeAShip(letters, numbers);
            gameField.print();
        }
    }

    private static int getNumberCoordinate(String coordinates) {
        int numberCoordinate;
        if (coordinates.length() == 2) {
            numberCoordinate = Integer.parseInt(String.valueOf(coordinates.charAt(1)));
        } else {
            numberCoordinate = Integer.parseInt(String.valueOf(coordinates.charAt(1))
                    + coordinates.charAt(2));
        }
        return numberCoordinate;
    }

    private static char getLetterCoordinate(String coordinates) {
        return coordinates.charAt(0);
    }

    private static String enterCoordinatesForShot() {
        return scanner.next();
    }

    private static String[] enterCoordinatesForShip() {
        String[] coordinates = new String[2];
        coordinates[0] = scanner.next();
        coordinates[1] = scanner.next();
        return coordinates;
    }

    private static void pressEnterToSwitchThePlayer() {
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
    }
}




