import java.util.Scanner;

public class Driver {
    private Team team;
    private Scanner input;

    //main method to run the app
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.runMenu();
    }

    //constructor, instanciates the scanner and creates the team with the users chosen size
    public Driver() {
        input = new Scanner(System.in);
        System.out.print("Enter the number of players on the team: ");
        int totalPlayers = input.nextInt();
        input.nextLine();
        team = new Team(totalPlayers);
    }


    //displays the menu and returns what the user has chosen
    public int showMenu() {
        System.out.println(""" 
                          TEAM SYSTEM 
                  ------------------------------------------------------
                      1) Add a player
                      2) Add player ratings
                      3) Add a player to the squad
                      4) Remove a player from the squad
                      5) List all players
                      6) List the players currently on this season's squad
                      7) List players who average rating is greater than a given rating
                      8) List the players name with their average rating
                      9) Display average player rating
                      10) Display player with the lowest average rating
                      11) Display player with the highest average rating
                      0) Exit
                ==>>""");

        int option = input.nextInt();
        return option;
    }


    /* runs the menu on a loop and handles the users input using the case/switch statement
     if the user enters 0, it will end the application */
    public void runMenu() {

        int option = showMenu();
        while (option != 0) {

            switch (option) {
                case 1 -> showAddPlayerForm();
                case 2 -> showPlayerRatingPrompt();
                case 3 -> showAddPlayerToSquad();
                case 4 -> showRemovePlayerFromSquad();
                case 5 -> showListOfPlayers();
                case 6 -> showSquadList();
                case 7 -> showAverageInputForm();
                case 8 -> showPlayersAndAverages();
                case 9 -> showOverallAverage();
                case 10 -> showLowestRatedPlayer();
                case 11 -> showHighestRatedPlayer();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid option entered: " + option);
            }
            System.out.println("\nPress enter key to continue...");
            input.nextLine();
            input.nextLine(); // bug in scanner class meaning a second nextLine is needed.
            option = showMenu();
        }
        System.out.println("Exiting...bye");
        System.exit(0);
    }


    // shows the user the menu to add a new player by pressing 1
    private void showAddPlayerForm() {

        if (team.getPlayerIndex() < team.getTotal()) {
            input.nextLine();

            System.out.print("Enter player name: ");
            String name = input.nextLine();

            int playerNumber;
            boolean numberExists;

            //keep asking for a player number until a new one is entered
            do {
                System.out.print("Enter player number (1-23): ");
                playerNumber = input.nextInt();

                //check if this number is already used
                numberExists = false;
                for (int i = 0; i < team.getPlayerIndex(); i++) {
                    if (team.getPlayer(i).getPlayerNumber() == playerNumber) {
                        numberExists = true;
                        System.out.println("That player number is already taken. Please enter a different number.");
                        break;
                    }
                }
            } while (numberExists);

            System.out.print("Is the player currently in the squad? (y/n): ");
            //uses if statement to handle if user enters lowercase or uppercase y
            char squadStatus = input.next().charAt(0);
            boolean currentSquadMember = false;
            if (squadStatus == 'y' || squadStatus == 'Y') {
                currentSquadMember = true;
            }

            int[] ratings = new int[6];

            team.add(new Player(name, playerNumber, ratings, currentSquadMember));
            System.out.println("Player added.");
        } else {
            System.out.println("The team is full. Cannot add more players.");
        }
    }


    private void showPlayerRatingPrompt() {
        //show all players with their index numbers
        System.out.println("List of players:");
        for (int i = 0; i < team.getPlayerIndex(); i++) {
            System.out.println(i + ": " + team.getPlayer(i).getName());
        }

        //ask the user to choose a player by number
        System.out.print("Which player number do you want to add ratings to? ");
        int playerNumber = input.nextInt();

        //check if the player number is valid
        if (playerNumber < 0 || playerNumber >= team.getPlayerIndex()) {
            System.out.println("That is not a valid player number.");
            return;
        }

        //create an array to store the 6 ratings
        int[] ratings = new int[6];

        //ask the user to enter 6 ratings between 0 and 5
        for (int i = 0; i < 6; i++) {
            System.out.print("Enter rating " + (i + 1) + " (0-5): ");
            int rating = input.nextInt();

            //check if the rating is between 0 and 5
            while (rating < 0 || rating > 5) {
                System.out.println("Rating must be between 0 and 5. Please try again.");
                System.out.print("Enter rating " + (i + 1) + " (0-5): ");
                rating = input.nextInt();
            }

            //store the rating in the array
            ratings[i] = rating;
        }

        //sets the ratings for the chosen player
        team.getPlayer(playerNumber).setRatings(ratings);

        //tell the user the ratings were added
        System.out.println("Ratings added for " + team.getPlayer(playerNumber).getName());
    }


    //lets the user add a player to the squad if they are not already in it
    private void showAddPlayerToSquad() {

        input.nextLine();
        System.out.println("Players: ");

        //displays the players with their index number
        for (int i = 0; i < team.getPlayerIndex(); i++) {
            System.out.println(i + ": " + team.getPlayer(i).getName());
        }

        System.out.println("Enter the index of the player you want to add: ");
        int index = input.nextInt();
        //retrieves the player at the users chosen index
        Player player = team.getPlayer(index);
        //checks if player is in squad already
        if (player.isCurrentSquadMember()) {
            System.out.println(player.getName() + " is already in squad.");

        } else {
            player.setCurrentSquadMember(true);
            System.out.println(player.getName() + " has been added.");

        }
    }


    private void showRemovePlayerFromSquad() {

        input.nextLine();
        //checks if any players are in the team
        if (team.isEmpty()) {
            System.out.println(" No players in the team.");
            return;
        }

        System.out.println("Players: ");
        for (int i = 0; i < team.getPlayerIndex(); i++) {
            System.out.println(i + ": " + team.getPlayer(i).getName());
        }

        System.out.println("Enter the index of the player you want to remove from the squad: ");
        int index = input.nextInt();
        //trys to remove the player from the squad using the team method
        Player removed = team.deRegisterPlayer(index);

        //checks if the player was actually in the squad and removed
        if (removed == null) {
            System.out.println("Player not removed - was already not in squad");
        } else {
            System.out.println(removed.getName() + " has been removed from the squad.");
        }
    }


    private void showListOfPlayers() {

        input.nextLine();
        System.out.println("List of all players: ");
        //uses a loop to go through all the players currently in the team
        for (int i = 0; i < team.getPlayerIndex(); i++) {
            //retrieves the player
            Player player = team.getPlayer(i);
            System.out.println("Index: " + i);
            System.out.println(player);
            //prints a blank line for more user-friendly readability
            System.out.println();
        }
    }


    private void showSquadList() {

        input.nextLine();
        System.out.println("Players that are currently in the squad: ");
        //string to list all the current squad members
        String result = team.listCurrentPlayers();
        //checks if result is empty - if so, return message
        if (result.isEmpty()) {
            System.out.println("There are no players currently in the squad.");
        } else {
            System.out.println(result);
        }
    }


    private void showAverageInputForm() {
        input.nextLine();
        System.out.println("Enter the rating(0-5): ");
        double numberAverage = input.nextDouble();
        //gets a list of players whos average rating is above the users enetered value
        String result = team.listPlayersAboveGivenAverageRating(numberAverage);
        //checks if empty, if so, return message
        if (result.isEmpty()) {
            System.out.println("No players have an average rating above " + numberAverage);
        } else {
            System.out.println("Players with an average rating greater than " + numberAverage + ":");
            System.out.println(result);
        }
    }


    //method to list players with their average ratings
    private void showPlayersAndAverages() {

        input.nextLine();
        System.out.println("List of players with average ratings: ");
        System.out.println(team.listOfPlayersWithAverageRating());
    }


    /*method to display the overall average of the players if they
    have been given an average rating */
    private void showOverallAverage() {

        double averageRating = team.averageOfPlayersAverageRating();
        if (averageRating != -1) {
            System.out.printf("The average of all players ratings is: %.2f", averageRating);
        } else {
            System.out.println("There are no average rating at this moment.(No players have a rating set)");
        }
    }


    /* method to display the player with the lowest average rating
    if they have been given an average rating */
    private void showLowestRatedPlayer() {

        Player lowestRatedPlayer = team.playerWithLowestAverageRating();
        if (lowestRatedPlayer != null) {
            System.out.println("The player with the lowest average rating: ");
            System.out.println(lowestRatedPlayer);
        } else {
            System.out.println("There is no player with the lowest rating right now.");
        }
    }


    /*method to display the player with the highest
    average rating if they have been given a rating */
    private void showHighestRatedPlayer() {

        Player highestRatedPlayer = team.playerWithHighestAverageRating();
        if (highestRatedPlayer != null) {
            System.out.println("The player with the highest average rating: ");
            System.out.println(highestRatedPlayer);
        } else {
            System.out.println("There is no player with the highest rating right now.");
        }
    }
}