package Model;

import java.util.ArrayList;

public class Player {

    private ArrayList<Strategy> strategies;
    private int id;

    public Player(int id) {
        this.id = id;
        this.strategies = new ArrayList<>();
    }

    // public int getId(){
    //     return this.id;
    // }

    public ArrayList<Strategy> getStrategies(){
        return strategies;
    }


    //The getBestResponses2 method takes another Player object and an integer n as parameters, and returns an ArrayList of strings that represent the best responses of this player to the other player's strategies. This method implements a search algorithm that finds the best response for each strategy of the other player, based on a value associated with each strategy.

    public ArrayList<String> getBestResponses2(Player otherPlayer, int n) {
        ArrayList<String> resp = new ArrayList<>();
        int index = 0;
        int iMax = 0, jMax = 0;
        for (int i = 0; i < n; i++) {
            index = 0;
            iMax = i;
            jMax = 0;
            int max = this.strategies.get(0).getValues().get(i);
            for (int j = 0; j < this.strategies.size(); j++) {
                if (max < this.strategies.get(j).getValues().get(i)) {
                    iMax = i;
                    jMax = j;
                    max = this.strategies.get(j).getValues().get(i);
                    index = j;
                } else if (max == this.strategies.get(j).getValues().get(i) && otherPlayer.strategies.get(i).getValues()
                        .get(j) > otherPlayer.strategies.get(iMax).getValues().get(jMax)) {
                    max = this.strategies.get(j).getValues().get(i);
                    index = j;
                }

            }
            addBestResponse(index, i, iMax, jMax, otherPlayer, resp);
        }

        return resp;
    }


    //The addBestResponse method is a helper method for getBestResponses2. It takes several parameters and adds a string to the ArrayList of responses, based on the values of the strategies.
    private void addBestResponse(int index, int i, int iMax, int jMax, Player otherPlayer, ArrayList<String> resp) {
        if (this.id == 1)
            resp.add("(" + this.strategies.get(index).getName() + "," + otherPlayer.strategies.get(i).getName() + ")");
        else
            resp.add("(" + otherPlayer.strategies.get(i).getName() + "," + this.strategies.get(index).getName() + ")");

        for (int j = 0; j < this.strategies.size(); j++) {
            if (this.strategies.get(index).getValues().get(i) == this.strategies.get(j).getValues().get(i)
                    && otherPlayer.strategies.get(i).getValues().get(j) == otherPlayer.strategies.get(iMax).getValues().get(jMax)
                    && j != index) {
                if (this.id == 1)
                    resp.add("(" + this.strategies.get(j).getName() + "," + otherPlayer.strategies.get(i).getName() + ")");
                else
                    resp.add("(" + otherPlayer.strategies.get(i).getName() + "," + this.strategies.get(j).getName() + ")");

            }
        }

    }

    //The addBestResponse method is a helper method for getBestResponses2. It takes several parameters and adds a string to the ArrayList of responses, based on the values of the strategies.
    

    /*
     * Compare between two Strategies Values if l1.val < l2.val @return -1 if @all
     * l1.values >= l2.values @return 1
     */
    //The compareStrategiesValues method takes two ArrayLists of integers and returns -1 if the first list has a smaller value than the second list, 1 if the first list has larger or equal values for all positions, and throws an exception if the two lists have different sizes.
    public int compareStrategiesValues(ArrayList<Integer> l1, ArrayList<Integer> l2) throws Exception {
        if (l1.size() != l2.size())
            throw new Exception("list of values have diffrent size");
        for (int i = 0; i < l1.size(); i++) {
            // System.out.println(l1.get(i)+" : " + l2.get(i));
            if (l1.get(i) < l2.get(i))
                return -1;
        }
        return 1;
    }

    /*
     * Find if exist equals values between dominant strategy and other strategies if
     * exist @return true else @return false
     */
    //The isEqualsValuesExist method takes two ArrayLists of integers and returns true if there is at least one position where the values are equal, and false otherwise.
    public boolean isEqualsValuesExist(ArrayList<Integer> l1, ArrayList<Integer> l2) throws Exception {
        if (l1.size() != l2.size())
            throw new Exception("list of values have diffrent size");
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i).equals(l2.get(i)))
                return true;
        }
        return false;
    }

    /*
     * Find dominant strategy and its dominated strategy if exist @return
     * {dominantStrategy, dominatedStrategy} else @return null
     */
    //The getDominantStrategy method returns an array of Strategy objects that represent the dominant and the dominated strategies of the player, if they exist. The method compares each pair of strategies in the player's list and finds the dominant strategy and the dominated strategy based on their values.
    public Strategy[] getDominantStrategy() {
        Strategy[] strategiesArray;
        int returnVal = 0;
        for (int i = 0; i < this.strategies.size(); i++) {
            for (Strategy strategy : this.strategies) {
                if (this.strategies.get(i).hashCode() == strategy.hashCode())
                    continue;

                try {
                    returnVal = compareStrategiesValues(this.strategies.get(i).getValues(), strategy.getValues());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (returnVal == 1) {
                    strategiesArray = new Strategy[2];
                    strategiesArray[0] = this.strategies.get(i);
                    strategiesArray[1] = strategy;
                    return strategiesArray;
                }
            }
        }
        return null;
    }

    /*
     * Find type of dominant strategy if it's weak @return weak else @return
     * strictly
     */
    public String isStrictlyOrWeaklyDominantStrategy(Strategy dominantStrategy, Strategy dominatedStrategy) {
        boolean isWeakDominant = false;

        try {
            isWeakDominant = isEqualsValuesExist(dominantStrategy.getValues(), dominatedStrategy.getValues());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isWeakDominant)
            return "weakly";

        return "strictly";
    }

    /*
     * Remove Dominated strategy
     * 
     * @return index of removed strategy
     */
    public int removeDominatedStrategy(Strategy dominatedStrategy) {
        int index = strategies.indexOf(dominatedStrategy);
        strategies.remove(dominatedStrategy);
        return index;
    }

    /*
     * Remove Values of removed strategy in other strategies
     */
    public void removeValuesByIndex(int index) {
        for (Strategy strategy : strategies) {
            strategy.getValues().remove(index);
        }
    }


}