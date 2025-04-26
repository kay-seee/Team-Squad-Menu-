public class Team {
    //fields - array to store Player objects
    private Player[] players;
    private int total;
    private int playerIndex = 0;

    //Constructor
    public Team(int total) {
        this.total = total;
        this.players = new Player[total];
    }

    //returns an array of the players
    public Player[] getPlayers() {
        return players;
    }

    //method to return the max number of players allowed on team
    public int getTotal() {
        return total;
    }

    //method to return the number of the players currently on the team
    public int getPlayerIndex() {
        return playerIndex;
    }

    //method to add player to the team - if there is space
    public void add(Player player) {
        if (playerIndex < total) {
            players[playerIndex++] = player;
        }
    }

    //method to return a player at a certain index or null if none
    public Player getPlayer(int index) {
        if (index >= 0 && index < playerIndex) {
            return players[index];
        }
        return null;
    }

    //method to return true is there are no players on the team
    public boolean isEmpty() {
        return playerIndex == 0;
    }

    //method to return a string - user-friendly list of players on the team
    public String listPlayers() {
        String result = "";
        for (int i = 0; i < playerIndex; i++) {
            result += players[i].toString() + "\n\n";
        }
        return result;
    }


    //method to return a string - user-friendly list of players currently on the squad
    public String listCurrentPlayers() {
        String result = "";
        for (int i = 0; i < playerIndex; i++) {
            if (players[i].isCurrentSquadMember()) {
                result += players[i].toString() + "\n\n";
            }
        }
        return result;
    }


    /*method to remove player from the squad when user enters index. the method sets their
    squad status to false. returns the player was removed if removed or null if not in the
    squad*/
    public Player deRegisterPlayer(int indexToDeRegister) {
        if (indexToDeRegister >= 0 && indexToDeRegister < playerIndex) {
            Player player = players[indexToDeRegister];
            if (player.isCurrentSquadMember()) {
                player.setCurrentSquadMember(false);
                return player;
            } else {
                return null;
            }
        }
        return null;
    }


    //method to list all the players with their average ratings
    public String listOfPlayersWithAverageRating() {
        String result = "";
        for (int i = 0; i < playerIndex; i++) {
            Player p = players[i];
            double avg = getAverageRating(p);
            result += "Name: " + p.getName() + ", Average Rating: " + String.format("%.2f", avg) + "\n";
        }
        return result;
    }


    //method to find and return the player with the lowest average rating
    public Player playerWithLowestAverageRating() {
        //checks if it is empty
        if (isEmpty()) return null;
        //assume player index 0 has the lowest
        Player lowest = players[0];
        double lowestAvg = getAverageRating(lowest);
        //loop through the rest to find if anyone else has lower
        for (int i = 1; i < playerIndex; i++) {
            double currentAvg = getAverageRating(players[i]);
            if (currentAvg < lowestAvg) {
                lowest = players[i];
                lowestAvg = currentAvg;
            }
        }
        return lowest;
    }


    //method to find and return the player with the highest average rating
    public Player playerWithHighestAverageRating() {
        if (isEmpty()) return null;
        //assumes player index 0 has the highest
        Player highest = players[0];
        double highestAvg = getAverageRating(highest);
        //loop through the rest to find if anyone else has higher
        for (int i = 1; i < playerIndex; i++) {
            double currentAvg = getAverageRating(players[i]);
            if (currentAvg > highestAvg) {
                highest = players[i];
                highestAvg = currentAvg;
            }
        }
        return highest;
    }


    //method to calculate and return the average rating for the chosen player
    public double getAverageRating(Player player) {
        //get the array of ratings for the player
        int[] ratings = player.getRatings();
        int sum = 0;
        //adds up all the ratings
        for (int rating : ratings) {
            sum += rating;
        }
        //returns the average, if none, return 0.0
        return ratings.length > 0 ? (double) sum / ratings.length : 0.0;
    }


    //method to list players who average rating is above the given rating
    public String listPlayersAboveGivenAverageRating(double averageAboveFig) {
        if (isEmpty()) {
            return "No players are on the team";
        } else {
            String str = "";
            //for loop to go through all players on the team
            for (int i = 0; i < playerIndex; i++) {
                if (getAverageRating(players[i]) > averageAboveFig) {
                    str += i + ": " + players[i] + "\n";
                }
            }
            //if no players are above users given value return message
            if (str.equals("")) {
                return "No players have an average rating above: " + averageAboveFig;
            } else {
                return str;
            }
        }
    }


    //method to calculate and return the average rating of the players overall
    public double averageOfPlayersAverageRating() {
        if (!isEmpty()) {
            //field to add up all players average ratings
            double totalAverage = 0;
            //field to add up how many players have ratings
            int count = 0;

            for (int i = 0; i < playerIndex; i++) {
                int[] ratings = players[i].getRatings();
                //only includes the players with ratings
                if (ratings.length > 0) {
                    int sum = 0;
                    //adds all the ratings for that player
                    for (int j = 0; j < ratings.length; j++) {
                        sum += ratings[j];
                    }
                    //calculates the players average rating
                    double average = (double) sum / ratings.length;
                    totalAverage += average;
                    count++;
                }
            }
            if (count > 0) {
                return totalAverage / count;
            }
        }
        return -1;
    }
}