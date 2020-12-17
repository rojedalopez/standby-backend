package co.com.bancolombia.jpa.repositories.componente;

import co.com.bancolombia.jpa.repositories.clavevalor.ClaveValorData;
import co.com.bancolombia.jpa.repositories.evento.EventoComponenteData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.hibernate.annotations.TypeDef(name = "ambientesJson", typeClass = AmbientesJson.class)
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "componente")
public class ComponenteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_componente;

    @Column
    private String nombre;

    @Column
    @Type(type = "ambientesJson")
    private AmbienteData[] ambientes;

    @ManyToOne
    @JoinColumn(name = "fk_tipocomponente", referencedColumnName = "id_clavevalor")
    private ClaveValorData fk_tipocomponente;

    @Column
    private String nota;

    @OneToMany(mappedBy = "fk_componente")
    private Set<EventoComponenteData> eventoComponente = new HashSet<>();


}
