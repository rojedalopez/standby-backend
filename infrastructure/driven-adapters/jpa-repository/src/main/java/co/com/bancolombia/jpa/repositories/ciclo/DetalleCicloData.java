package co.com.bancolombia.jpa.repositories.ciclo;

import co.com.bancolombia.jpa.repositories.usuario.UsuarioData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "detalleciclo")
public class DetalleCicloData {

    @EmbeddedId
    private DetalleCicloPK id_detallecliclo;

    @Column(name = "inicio")
    private Date inicio;

    @Column(name = "fin")
    private Date fin;

    @ManyToOne
    @MapsId("fk_usuario")
    @JoinColumn(name = "fk_usuario")
    private UsuarioData fk_usuario;

    @ManyToOne
    @MapsId("fk_ciclo")
    @JoinColumn(name = "fk_ciclo")
    private CicloData fk_ciclo;

}
