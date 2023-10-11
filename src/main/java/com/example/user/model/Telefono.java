package com.example.user.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
@Entity
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String citycode;
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
