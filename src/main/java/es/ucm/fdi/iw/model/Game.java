package es.ucm.fdi.iw.model;

import lombok.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.persistence.*;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.ucm.fdi.iw.controller.UserController;

@Entity
@Data
@NoArgsConstructor
@Table(name="Game")
public class Game implements Transferable<Game.Transfer> {

    private static final int EXPLODING_TIME = 30;
    private static final int NUMPLAYERS = 1;
    private static final int IFX_LENGTH = 3;
    private static final String ALPHABET = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private static final int ROUNDS = 0;
    private static final boolean PRIV = true;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    private Random rand = new Random();

    private int explodingTime;
    private int ifxLength;
    private int numPlayers;

    
	@OneToMany
	@JoinColumn(name = "recipient_id")	
	private List<Message> received = new ArrayList<>();


    @Column
    @ElementCollection
    private List<Letter> alphabet;

    public enum GameState {
        LOBBY,
        GAME,
        FINISHED
    };

    private GameState state;
    private int rounds;
    private boolean priv;
    private String interfix;

    private String topicCode;

    @OneToOne
    private Player currPlayer; 
    private int playerIdx;

    @OneToOne
    private Player creator; 

    // for the summary
    @OneToOne
    private Player winner;
    @OneToOne
    private Player second;
    @OneToOne
    private Player third;
    @OneToOne
    private Player fourth;
    @OneToOne
    private Player fifth;

    @OneToMany
    @JoinColumn(name="game_id")
    private List<Player> players;

    @Column
    @ElementCollection
    private List<String> wordList;

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

    @Transactional
    public void initGame(User ucreator,  Player creator) {
        explodingTime = EXPLODING_TIME;
        ifxLength = IFX_LENGTH;
        numPlayers = NUMPLAYERS;

        topicCode = UserController.generateRandomBase64Token(6);
        
        alphabet = new ArrayList<Letter>();
        for(String letter: Arrays.asList(ALPHABET.split(""))){
            Letter n = new Letter();
            n.setB(true);
            n.setLetter(letter);
            alphabet.add(n);
        }
        
        state =  GameState.LOBBY;
        rounds = ROUNDS;
        priv = PRIV;

        try {
            initWordList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        interfix = rndIfx();

        creator.initPlayer(this, ucreator, ROUNDS);
        players = new ArrayList<Player>();
        players.add(creator);
        this.creator = creator;
        currPlayer = creator;
        playerIdx = 0;
    }

    public boolean playerExist(User user){
        for(Player p: players){
            if (p.getUser().getId() == user.getId())
                return true;
        }
        return false;
    }

    public void initWordList() throws FileNotFoundException{
        wordList = new ArrayList<String>();
        try (Scanner s = new Scanner(Game.class.getResourceAsStream("/static/docs/wordList.txt"))) {
            while (s.hasNext()){
                wordList.add(s.next());
            }
            s.close();
        }
    }

    public List<String> getWordList(){
        return wordList;
    }

    public boolean isWord(String word) {
        return wordList.contains(word);
    }

    public String rndIfx() {
        int rnd;
        String rndWord;
        // Get random word of at least length ifx_length
        do {
            rnd = rand.nextInt(wordList.size());
            rndWord = wordList.get(rnd);
        } while(rndWord.length() < ifxLength);

        System.out.println(rndWord);
        // Generate all possible intervals of length ifx_length
        List<String> possible_ifx = new ArrayList<String>();

        for(int i=0; i<= rndWord.length(); i++) {
            for(int j=rndWord.length(); j>=i; j--) {
            	String pIfx = rndWord.substring(i, j);
                if (pIfx.length() == ifxLength) {
                    possible_ifx.add(pIfx);
                }
            }
        }

        rnd = rand.nextInt(possible_ifx.size());
        return possible_ifx.get(rnd).toUpperCase();
    }

    public void updateAlphabet(String letter) {
        for (Letter l: alphabet)
            if(l.getLetter().charAt(0) == letter.charAt(0))
                l.setB(!(l.getB()));

        for (Player p: players)
            p.updateAlphabet(letter);
    }

    public void nextTurn(){
        playerIdx = (playerIdx + 1) % players.size();
        if (players.get(playerIdx).getLives() <= 0)
            nextTurn();
        this.setCurrPlayer(players.get(playerIdx));
    }

    public void playerDies() {
        // ranking
        currPlayer.setRank(numPlayers);
        numPlayers--;
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        numPlayers++;
    }

    public void getRanking() {
        for (int i = 0; i < 5 && i < players.size(); i++){
            Player p = players.get(i);
            switch (p.getRank()){
                case 1:
                    winner = p;
                    break;
                case 2:
                    second = p;
                    break;
                case 3:
                    third = p;
                    break;
                case 4:
                    fourth = p;
                    break;
                case 5:
                    fifth = p;
                    break;
            }
        }
    }
}
