package es.ucm.fdi.iw.model;

import lombok.*;

import java.util.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="Game")
public class Game implements Transferable<Game.Transfer> {

    private static final int _EXPLODING_TIME = 30;
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

    private int exploding_time;
    private int ifx_length;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> topics;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> alphabet;
    
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
    private List<Player> players = new ArrayList<>();

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

    public void init_game() {
        
    }
}
