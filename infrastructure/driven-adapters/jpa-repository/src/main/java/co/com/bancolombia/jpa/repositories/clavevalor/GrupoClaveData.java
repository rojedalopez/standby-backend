package co.com.bancolombia.jpa.repositories.clavevalor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "grupoclave")
public class GrupoClaveData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_grupoclave;

    @Column
    private String nombre;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean activo = true;

    @OneToMany
    private List<ClaveValorData> claveValorData;
}
