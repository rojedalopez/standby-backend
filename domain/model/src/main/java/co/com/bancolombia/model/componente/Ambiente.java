package co.com.bancolombia.model.componente;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Ambiente {
    private String nombre;
    private String ubicacion;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ambiente)) return false;
        Ambiente ambiente = (Ambiente) o;
        return Objects.equals(getNombre(), ambiente.getNombre()) &&
                Objects.equals(getUbicacion(), ambiente.getUbicacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getUbicacion());
    }
}
