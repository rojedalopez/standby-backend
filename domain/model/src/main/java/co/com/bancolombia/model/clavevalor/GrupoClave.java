package co.com.bancolombia.model.clavevalor;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GrupoClave {
    private int id_grupoclave;
    private String nombre;
    private boolean activo;
}
