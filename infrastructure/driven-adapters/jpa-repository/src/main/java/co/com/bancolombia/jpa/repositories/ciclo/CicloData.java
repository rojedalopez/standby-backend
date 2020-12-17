package co.com.bancolombia.jpa.repositories.ciclo;

import co.com.bancolombia.jpa.repositories.clavevalor.ClaveValorData;
import co.com.bancolombia.jpa.repositories.usuario.UsuarioData;
import co.com.bancolombia.model.clavevalor.ClaveValor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ciclo")
public class CicloData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date inicio;

    @Column
    private Date fin;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    private UsuarioData fk_usuario;

    @ManyToOne
    @JoinColumn(name = "fk_subdominio", referencedColumnName = "id_clavevalor")
    private ClaveValorData fk_subdomio;

    @Column
    private Boolean activo;

    @OneToMany(mappedBy = "fk_ciclo")
    private Set<DetalleCicloData> detalleCicloDataSet;

}
