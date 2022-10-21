import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        boolean isGameOver = false;
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        int points1 = 0;
        int points2 = 0;

        System.out.println(player1.name + ", place your ships on the game field");
        player1.battlefield.init();
        pressEnterToSwitchThePlayer();

        System.out.println(player2.name + ", place your ships on the game field");
        player2.battlefield.init();
        pressEnterToSwitchThePlayer();

        while (true) {
            player2.battlefield.fogOfWarField.print();
            System.out.println("---------------------");
            player1.battlefield.gameField.print();
            System.out.println("\n" +  player1.name + ", it's your turn:");
            if (player2.battlefield.game() == 1){
                points1++;
                System.out.println("Player 1 points : " + points1);
            }
            if (points1 == 17) {
                break;
            }
            pressEnterToSwitchThePlayer();


            player1.battlefield.fogOfWarField.print();
            System.out.println("---------------------");
            player2.battlefield.gameField.print();
            System.out.println("\n" +  player2.name + ", it's your turn:");
            if (player1.battlefield.game() == 1){
                points2++;
                System.out.println("Player 2 points : " + points2);
            }
            if (points2 == 17) {
                break;
            }
            pressEnterToSwitchThePlayer();
        }
    }

    private static void pressEnterToSwitchThePlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
    }
}
