package com.example.user.model;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Telefono {
    private String number;
    private String citycode;
    private String contrycode;
}
