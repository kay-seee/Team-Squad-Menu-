import java.util.Arrays;

public class Player {
    //fields to store the players information
    private String name = "";
    private int playerNumber = 0;
    private int[] ratings = new int[6];
    private boolean currentSquadMember = false;

    //Constructor - instanciates the new player with the given details
    public Player(String name, int playerNumber, int[] ratings, boolean currentSquadMember) {
        this.name = name.length() > 20 ? name.substring(0, 20) : name;
        this.playerNumber = playerNumber;
        this.setRatings(ratings);
        this.currentSquadMember = currentSquadMember;
    }

    //getter method to return player name
    public String getName() {
        return name;
    }

    //setter method to update players name(max 20 chars)
    public void setName(String name) {
        if (name != null) {
            this.name = name.length() > 20 ? name.substring(0, 20) : name;
        }
    }

    //getter method to get players number
    public int getPlayerNumber() {
        return playerNumber;
    }

    //setter method to update the players number between 1-23, otherwise 23
    public void setPlayerNumber(int number) {
        if (number >= 1 && number <= 23) {
            this.playerNumber = number;
        } else {
            this.playerNumber = 23;
        }
    }

    //getter method to return all ratings in an array
    public int[] getRatings() {
        return ratings;
    }

    //setter method to update the player ratings array
    public void setRatings(int[] ratings) {
        if (ratings != null) {
            for (int i = 0; i < ratings.length && i < 6; i++) {
                if (ratings[i] >= 0 && ratings[i] <= 5) {
                    this.ratings[i] = ratings[i];
                } else {
                    this.ratings[i] = 0;
                }
            }
        }
    }

    //method to return a single rating of the users index choice
    public int getRating(int index) {
        if (index >= 0 && index < ratings.length) {
            return ratings[index];
        }
        return -1;
    }

    //method to update a single array
    public void setRating(int index, int value) {
        if (index >= 0 && index < ratings.length && value >= 0 && value <= 5) {
            ratings[index] = value;
        }
    }

    //method to check if player is in the squad, if so - returns true
    public boolean isCurrentSquadMember() {
        return currentSquadMember;
    }

    //setter method to update the squad membership status
    public void setCurrentSquadMember(boolean currentSquadMember) {
        this.currentSquadMember = currentSquadMember;
    }

    //returns a formatted user-friendly string/sentence with all the players information
    public String toString() {
        return "Name: " + name +
                "\nPlayer number: " + playerNumber +
                "\nRating: " + Arrays.toString(ratings) +
                "\nCurrent Squad Member: " + currentSquadMember;
    }
}