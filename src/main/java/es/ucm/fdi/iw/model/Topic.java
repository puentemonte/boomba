package es.ucm.fdi.iw.model;

import javax.persistence.*;

import lombok.*;


@Embeddable  
@Data
@NoArgsConstructor
public class Topic {
    private String topic;
    private boolean b;
}
