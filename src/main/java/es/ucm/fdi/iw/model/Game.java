package es.ucm.fdi.iw.model;

import lombok.*;

import java.util.List;

import javax.persistence.*;

@Entity
@Data
public class Game {
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
}
