package steve.decker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Deck {
    private ArrayList<Integer> deck = new ArrayList<>();
    private int numberOfPairs;
    private Hashtable<Integer, Integer> seenCards = new Hashtable<>(); //Key is "wrapping paper color", value is index in deck
    private Hashtable<Integer, Integer> knownMatches = new Hashtable<>(); //Key is one index in deck, value is other index in deck
    private int lowestUnknownIndex = 0;

    public Deck(int numberOfPairs) {
        this.numberOfPairs = numberOfPairs;
        for(int i = 0; i < numberOfPairs; i++){
            this.deck.add(i);
            this.deck.add(i);
        }
        Collections.shuffle(this.deck);
    }

    public ArrayList<Integer> getDeck() {
        return deck;
    }

    public Hashtable<Integer, Integer> getSeenCards() {
        return seenCards;
    }

    public Hashtable<Integer, Integer> getKnownMatches() {
        return knownMatches;
    }

    public int getLowestUnknownIndex() {
        return lowestUnknownIndex;
    }

    public int getNumberOfPairs() {
        return numberOfPairs;
    }

    public void incrementUnknownIndex(){
        this.lowestUnknownIndex++;
    }

    public void removeCards (int index1, int index2, Player currentPlayer){
        this.deck.set(index1, null);
        this.deck.set(index2, null);
        currentPlayer.gainPoint();
    }

    public void addSeenCard(int index){
        this.seenCards.put(deck.get(index),index);
    }

    public void addKnownMatch(int index){
        this.knownMatches.put(index, this.seenCards.get(this.deck.get(index)));
    }

    public void removeKnownMatch(int key){
        this.knownMatches.remove(key);
    }

    @Override
    public String toString(){
        return this.deck.toString();
    }

}
