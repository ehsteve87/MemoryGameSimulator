package steve.decker;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int player1wins = 0;
        int player2wins = 0;
        for (int i = 0; i < 1000000; i++) {
            int winner = playGame();
            if (winner == 1)
                player1wins++;
            if (winner == 2)
                player2wins++;
        }
        System.out.println("Player 1 won " + player1wins + " times and player 2 won " + player2wins + " times.");

    }

    public static int playGame(){
        int deckSize = 50; //This value MUST be twice an odd number
        Deck gameDeck = new Deck(deckSize / 2);
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player currentPlayer = player1;
        Player nextPlayer = player2;


        while (player1.getPoints() < deckSize / 4 + 1 && player2.getPoints() < deckSize / 4 + 1){
            Player[] nextTurn = takeTurn(currentPlayer, nextPlayer, gameDeck);
            currentPlayer = nextTurn[0];
            nextPlayer = nextTurn[1];

        }
        if (player1.getPoints() > player2.getPoints()) {
            return 1;
        }
        return 2;

    }

    public static Player[] takeTurn(Player currentPlayer, Player nextPlayer, Deck gameDeck){
        int index = gameDeck.getLowestUnknownIndex();
        int nextCard = gameDeck.getDeck().get(index);


        //if there's a known pair, take it
        if (!gameDeck.getKnownMatches().isEmpty()){
            int key = (Integer) gameDeck.getKnownMatches().keySet().toArray()[0];
            int value = gameDeck.getKnownMatches().get(key);
            gameDeck.removeCards(key, value, currentPlayer);
            gameDeck.removeKnownMatch(key);
            Player playerArray[] = {currentPlayer, nextPlayer};
            return playerArray;
        }
        //if first card is in table of seen cards, take it
        if (gameDeck.getSeenCards().get(nextCard) != null){
            gameDeck.removeCards(index, gameDeck.getSeenCards().get(gameDeck.getDeck().get(index)), currentPlayer);
            gameDeck.incrementUnknownIndex();
            Player playerArray[] = {currentPlayer, nextPlayer};
            return playerArray;
        }
        //if first card hasn't been seen before, add it to seen cards table
        gameDeck.addSeenCard(index);
        int firstCard = gameDeck.getDeck().get(index);
        gameDeck.incrementUnknownIndex();
        index = gameDeck.getLowestUnknownIndex();

        //if second card matches first, remove both
        int secondCard = gameDeck.getDeck().get(index);
        if (secondCard == firstCard){
            gameDeck.removeCards(index, gameDeck.getSeenCards().get(secondCard), currentPlayer);
            gameDeck.incrementUnknownIndex();
            Player playerArray[] = {currentPlayer, nextPlayer};
            return playerArray;
        }

        //if second card is known match, add to list of known matches

        if(gameDeck.getSeenCards().get(secondCard) != null){
            gameDeck.addKnownMatch(index);
            gameDeck.incrementUnknownIndex();
            Player playerArray[] = {nextPlayer, currentPlayer};
            return playerArray;
        }

        //if second card isn't a known match, add to seen cards and end turn
        if(gameDeck.getSeenCards().get(gameDeck.getDeck().get(index)) == null){
            gameDeck.addSeenCard(index);
            gameDeck.incrementUnknownIndex();
            Player playerArray[] = {nextPlayer, currentPlayer};
            return playerArray;
        }
        //this line is just so the compiler doesn't yell at me
        Player playerArray[] = {nextPlayer, currentPlayer};
        return playerArray;
    }


}
