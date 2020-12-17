package co.com.bancolombia.model.componente;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Componente {
    private int id_componente;

    @NotEmpty
    private String nombre;

    @NotNull
    private Ambiente[] ambientes;

    @NotNull
    private ClaveValor fk_tipocomponente;

    private String nota;
}

