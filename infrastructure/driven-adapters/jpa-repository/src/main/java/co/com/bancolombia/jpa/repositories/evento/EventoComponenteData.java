package co.com.bancolombia.jpa.repositories.evento;

import co.com.bancolombia.jpa.repositories.componente.ComponenteData;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "eventocomponente")
public class EventoComponenteData implements Serializable {

    @EmbeddedId
    private EventoComponentePK id_eventocomponente = new EventoComponentePK();

    @ManyToOne
    @MapsId("fk_evento")
    @JoinColumn(name = "fk_evento", referencedColumnName = "id_evento")
    private EventoData fk_evento;

    @ManyToOne
    @MapsId("fk_componente")
    @JoinColumn(name = "fk_componente", referencedColumnName = "id_componente")
    private ComponenteData fk_componente;
}
