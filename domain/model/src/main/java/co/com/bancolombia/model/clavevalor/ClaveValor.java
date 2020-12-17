package co.com.bancolombia.model.clavevalor;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClaveValor {
    public int id_clavevalor;
    public GrupoClave fk_grupoclave;
    public String clave;
    public String valor;
    @Builder.Default
    public boolean activo = true;
}
