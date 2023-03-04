package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name="Player")
public class Player {
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
}