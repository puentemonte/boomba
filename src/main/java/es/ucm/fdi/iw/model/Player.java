package es.ucm.fdi.iw.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@Table(name="Player")
public class Player implements Transferable<Player.Transfer> {

    private static final int RANK = 0;
    private static final int LIVES = 3;
    private static final String ALPHABET = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private Game game;

    private int rounds;
    private int rank;
    private int lives;
    
    @Column
    @ElementCollection
    private List<Letter> alphabet; 

    @ManyToOne
    private User user;

    @Getter
    @AllArgsConstructor
    public static class Transfer {
        private long id;
    }

    @Override
    public Transfer toTransfer() {
        return new Transfer(id);
    }

    @Override
    public String toString() {
        return toTransfer().toString();
    }

    @Override
    public int hashCode() { return (int) id; }

    public void initPlayer(Game game, User user, int rounds) {
        this.game = game;
        this.user = user;
        this.rounds = rounds;
        lives = LIVES;
        rank = RANK;
        //alphabet = game.getAlphabet();
        alphabet = new ArrayList<Letter>();
        for(Letter letter: game.getAlphabet()){
            Letter newLetter = new Letter();
            newLetter.setLetter(letter.getLetter());
            newLetter.setB(letter.getB());
            alphabet.add(newLetter);
        }
    }

    public void updateAlphabet(String word){
        for (Letter l: alphabet)
            if(l.getLetter().charAt(0) == word.charAt(0))
                l.setB(!(l.getB()));
    }

    public void looseLife() {
        lives--;
    }

    public void setLetters(String upperCase) {
        for(int i = 0; i < upperCase.length(); ++i)
            for (Letter l: alphabet)
                if(l.getLetter().charAt(0) == upperCase.charAt(i))
                    l.setB(false);
    }

    public boolean completeAlphabet() {
        for (Letter l: alphabet)
            if (l.getB())
                return false;
        return true;
    }

    public void addLife() {
        lives++;
    }
}