package co.com.bancolombia.model.evento;

import co.com.bancolombia.model.componente.Componente;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventoComponente {

    private Componente fk_componente;

    private Evento fk_evento;
}
