package eus.evernature.evern.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity(name = "camara")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Camera {

    @Id
    @Column(name = "camara_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "camera", cascade = CascadeType.ALL)
    List<Record> records = new ArrayList<>();

    @Column(name = "numero_zona")
    private Integer zoneNumber;

    @Column(name = "nombre_zona")
    private String zoneName;

}
