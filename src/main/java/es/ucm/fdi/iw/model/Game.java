package es.ucm.fdi.iw.model;

import lombok.*;

import java.util.*;

import javax.persistence.*;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@Table(name="Game")
public class Game implements Transferable<Game.Transfer> {

    private static final int _EXPLODING_TIME = 30;
    private static final int _NUMPLAYERS = 2;
    private static final int _IFX_LENGTH = 3;
    private static final String _TOPICS = "ALL";
    private static final String _ALPHABET = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private static final String _STATE = "LOBBY";
    private static final int _ROUNDS = 0;
    private static final boolean _PRIV = true;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    private int explodingTime;
    private int ifxLength;
    private int numPlayers;

    /*@Column
    @ElementCollection
    @CollectionTable(name = "game_topics", joinColumns = { @JoinColumn(name = "game_id") })
    @MapKeyColumn(name = "key")
    private Map<String, Boolean> topics;

    @Column
    @ElementCollection
    @CollectionTable(name = "game_alphabet", joinColumns = { @JoinColumn(name = "game_id") })
    @MapKeyColumn(name = "key")
    private Map<String, Boolean> alphabet;*/

    @Column
    @ElementCollection
    private List<Letter> alphabet;

    @Column
    @ElementCollection
    private List<Topic> topics;

    private String state;
    private int rounds;
    private boolean priv;
    private String interfix;

    @OneToOne
    private Player curr_player; 

    @OneToOne
    private Player creator; 

    @OneToMany
    @JoinColumn(name="game_id")
    private List<Player> players;

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
        explodingTime = _EXPLODING_TIME;
        ifxLength = _IFX_LENGTH;
        numPlayers = _NUMPLAYERS;

        topics = new ArrayList<Topic>();
        for(String topic: Arrays.asList(_TOPICS.split(" "))){
            Topic n = new Topic();
            n.setB(true);
            n.setTopic(topic);
            topics.add(n);
        }
        
        alphabet = new ArrayList<Letter>();
        for(String letter: Arrays.asList(_ALPHABET.split(""))){
            Letter n = new Letter();
            n.setB(true);
            n.setLetter(letter);
            alphabet.add(n);
        }
            

        state =  _STATE;
        rounds = _ROUNDS;
        priv = _PRIV;

        creator.initPlayer(this, ucreator, _ROUNDS);
        players = new ArrayList<Player>();
        players.add(creator);
        this.creator = creator;
    }

    public boolean playerExist(User user){
        for(Player p: players){
            if (p.getUser().getId() == user.getId())
                return true;
        }
        return false;
    }
}
