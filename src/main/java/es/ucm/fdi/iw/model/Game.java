package es.ucm.fdi.iw.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="Game")
public class Game implements Transferable<Game.Transfer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    private int exploding_time;
    private int ifx_length;
    private List<String> topics;
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
}
