package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
}
