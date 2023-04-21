package controller;

import java.util.ArrayList;

import javax.swing.JTextField;

import Model.Player;
import Model.Strategy;

public class FirstWindowController {

    public int M, N;
    public Player p1, p2;

    public FirstWindowController() {
        p1 = new Player(1);
        p2 = new Player(2);
    }

    //The doneAction is called when the user has entered the number of rows and columns in the payoff matrix in the first window of the application. The method parses the input strings as integers and generates the names of the strategies for each player based on the number of rows/columns.
    
    public void doneAction(String rowCount, String colCount) {

        M = Integer.parseInt(rowCount);
        N = Integer.parseInt(colCount);

        generateStrategiesName(p1, "A", M);
        generateStrategiesName(p2, "B", N);

    }

    //The okAction method is called when the user has entered the payoff values for each cell in the payoff matrix in the first window of the application. The method reads the values from the input fields and stores them in the corresponding Strategy objects for each player.
    public void okAction(ArrayList<JTextField> textFields) {
        readMatrix(textFields);
    }

    //The readMatrix method reads the payoff values from the input fields and stores them in the corresponding Strategy objects for each player. The method uses two nested loops to iterate over all cells in the payoff matrix and adds the values to the Values list of the corresponding Strategy object.
    private void readMatrix(ArrayList<JTextField> textFieldsList) {
        int cpt = 0;
        // Read the matrix values
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                p1.getStrategies().get(i).getValues().add(Integer.valueOf(textFieldsList.get(cpt++).getText()));
                p2.getStrategies().get(j).getValues().add(Integer.valueOf(textFieldsList.get(cpt++).getText()));
            }
        }
    }

    //The generateStrategiesName method generates the names of the strategies for a player based on the number of rows/columns in the payoff matrix. The method creates Strategy objects with names "A0", "A1", ..., "An-1" or "B0", "B1", ..., "Bn-1", and adds them to the Strategies list of the corresponding Player object.
    private void generateStrategiesName(Player p, String name, int size) {
        for (int k = 0; k < size; k++)
            p.getStrategies().add(new Strategy(name + k, new ArrayList<>()));
    }

    //The getNashEqualibrium2 method returns a list of Nash equilibrium pairs for the game. The method calls the getBestResponses2 method for each player with the other player as an argument, and checks if the two returned lists of strategies have any strategies in common. If so, the method adds the common strategy to the nashList.
    public ArrayList<String> getNashEqualibrium2() {
        ArrayList<String> response1 = p1.getBestResponses2(p2, N);
        ArrayList<String> response2 = p2.getBestResponses2(p1, M);

        ArrayList<String> nashList = new ArrayList<>();

        for (int i = 0; i < response1.size(); i++) {
            for (int j = 0; j < response2.size(); j++) {
                if (response1.get(i).equals(response2.get(j))) {
                    nashList.add(response1.get(i));
                }
            }
        }

        return nashList;
    }

    //The displaySecuredStrategy2 method returns a string that displays the secured strategy for a player. The method takes as input the name of the player and a Strategy object and returns a string that includes the name of the player, the name of the strategy, and the minimum value in the Values list of the Strategy object.
    public String displaySecuredStrategy2(String playerName, Strategy s) {
        return new StringBuilder().append("Secured Strategies of ").append(playerName).append(": ").append(s.getName())
                .append(" (").append(s.getMinValue()).append(")").toString();
    }

    //The getPareto method returns a list of Pareto-optimal strategies for the game. The method uses four nested loops to iterate over all possible pairs of strategies for the two players, and checks if any other pair of strategies dominates the current pair. If not, the method adds the current pair to the paretoList.


    

    //return the p1
    public Player getPlayer1() {
        return p1;
    }

    //return the p2
    public Player getPlayer2() {
        return p2;
    }

    //The getDominantMsg method returns a string that describes the dominant strategy for a player. The method takes as input a Player object and returns a string that includes the name of the dominant strategy, whether it is strictly or weakly dominant, and the name of the strategy that dominates it.
    public String getDominantMsg(Player p) {

        String label = p.getDominantStrategy()[1].getName() + " is "
                + p.isStrictlyOrWeaklyDominantStrategy(p.getDominantStrategy()[0], p.getDominantStrategy()[1])
                + " dominated by " + p.getDominantStrategy()[0].getName();

        return label;
    }

    //The remove method removes the dominated strategy from a player. The method takes as input a Player object and a Player object. The method removes the dominated strategy from the dominated strategy list of the Player object.
    public void remove(Player currentPlayer, Player otherPlayer) {
        // Remove dominated strategy
        int indexOfRemovedStrategy = currentPlayer.removeDominatedStrategy(currentPlayer.getDominantStrategy()[1]);
        otherPlayer.removeValuesByIndex(indexOfRemovedStrategy);

        if (currentPlayer == p1)
            M--;
        else
            N--;
    }

    //The showMsg method returns a string that describes the dominant strategy for a player. The method takes as input a Player object and returns a string that includes the name of the dominant strategy, whether it is strictly or weakly dominant, and the name of the strategy that dominates it.
    public String showMsg(String label, ArrayList<String> list) {
        StringBuilder Str = new StringBuilder(label);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Str.append(list.get(i)).append(" ");
            }
        } else
            Str.append("Doesn't exist");

        return Str.toString();
    }

    //The getGameIssueMsg method returns a string that describes the dominant strategy for a player. The method takes as input a Player object and returns a string that includes the name of the dominant strategy, whether it is strictly or weakly dominant, and the name of the strategy that dominates it.
    public String getGameIssueMsg() {
        StringBuilder label = new StringBuilder();
        if (p1.getStrategies().size() == 1 && p2.getStrategies().size() == 1) {

            label.append("Game outcome:  (").append(p1.getStrategies().get(0).getValues().get(0)).append(",")
                    .append(p2.getStrategies().get(0).getValues().get(0)).append(")");

        } else
            label.append("No dominant strategy");

        return label.toString();
    }
}
