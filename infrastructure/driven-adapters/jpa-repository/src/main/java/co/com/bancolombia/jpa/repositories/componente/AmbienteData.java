package co.com.bancolombia.jpa.repositories.componente;

import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class AmbienteData implements Serializable{
    private String nombre;
    private String ubicacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmbienteData)) return false;
        AmbienteData ambiente = (AmbienteData) o;
        return Objects.equals(getNombre(), ambiente.getNombre()) &&
                Objects.equals(getUbicacion(), ambiente.getUbicacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getUbicacion());
    }

}