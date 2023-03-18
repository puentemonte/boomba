package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name="Player")
public class Player {

    private static final int _RANK = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private Game game;

    private int rounds;
    private int rank;

    @ManyToOne
    private User user;

    public void init_player(Game game, User user, int rounds) {
        this.game = game;
        this.user = user;
        this.rounds = rounds;
        rank = _RANK;
    }
}