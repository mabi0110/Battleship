import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EnumShip[] ships = EnumShip.values();
        Field field = new Field();
        takePosition(field, ships);
        System.out.println("\n" + "The game starts!");
        theFirstShot(field);
    }

    private static void theFirstShot(Field field) {
        field.print();
        System.out.println("\n" + "Take a shot!");

        char letter;
        int number;
        String coordinates;

        boolean error = true;
        do {
            try {
                System.out.print("\n" + "> ");
                coordinates = enterCoordinatesForShot();
                letter = getLetterCoordinate(coordinates);
                number = getNumberCoordinate(coordinates);
                field.checkIfShotCoordinatesAreCorrect(letter, number);
                error = false;
                field.updateGameField(letter, number);
            } catch (EnteredWrongCoordinatesException e) {
                System.out.println(e.getMessage());
            }
        } while (error);
    }
    private static void takePosition(Field field, EnumShip[] ships) {
        field.print();
        char letter1, letter2;
        int number1, number2;
        String coordinates1, coordinates2;
        char[] letters = new char[2];
        int[] numbers = new int[2];

        for (EnumShip ship : ships) {
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
                    field.checkIfCoordinatesAreCorrect(letters, numbers, ship);
                    error = false;
                } catch (WrongShipLocationException | WrongLengthOfShipException | TooCloseToAnotherOneException e){
                    System.out.println(e.getMessage());
                }
            } while (error);

            field.placeAShip(letters, numbers);
            field.print();
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
}




