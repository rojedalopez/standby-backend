package co.com.bancolombia.jpa.repositories.ciclo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class DetalleCicloPK implements Serializable {
    @Column(name = "fk_usuario")
    private String fk_usuario;

    @Column(name = "fk_cliclo")
    private int fk_ciclo;

    @Column(name = "posicion")
    private int posicion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleCicloPK)) return false;
        DetalleCicloPK that = (DetalleCicloPK) o;
        return getFk_ciclo() == that.getFk_ciclo() &&
                getPosicion() == that.getPosicion() &&
                Objects.equals(getFk_usuario(), that.getFk_usuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFk_usuario(), getFk_ciclo(), getPosicion());
    }
}
