package es.ucm.fdi.iw.model;

import javax.persistence.*;

import lombok.*;

@Embeddable  
@Data
@NoArgsConstructor
public class Letter {
    private String letter;
    private boolean b;
}
