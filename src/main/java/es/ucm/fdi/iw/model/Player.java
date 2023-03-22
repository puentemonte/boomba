package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@Table(name="Player")
public class Player implements Transferable<Player.Transfer> {

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

    public void initPlayer(Game game, User user, int rounds) {
        this.game = game;
        this.user = user;
        this.rounds = rounds;
        rank = _RANK;
    }
}