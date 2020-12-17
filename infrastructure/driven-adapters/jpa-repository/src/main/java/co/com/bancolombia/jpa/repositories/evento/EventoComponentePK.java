package co.com.bancolombia.jpa.repositories.evento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventoComponentePK implements Serializable {

    private int fk_evento;

    private int fk_componente;

    public EventoComponentePK(){}

    public EventoComponentePK(int fk_evento, int fk_componente) {
        this.fk_evento = fk_evento;
        this.fk_componente = fk_componente;
    }

    public int getFk_evento() {
        return fk_evento;
    }

    public void setFk_evento(int fk_evento) {
        this.fk_evento = fk_evento;
    }

    public int getFk_componente() {
        return fk_componente;
    }

    public void setFk_componente(int fk_componente) {
        this.fk_componente = fk_componente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventoComponentePK)) return false;
        EventoComponentePK that = (EventoComponentePK) o;
        return getFk_evento() == that.getFk_evento() &&
                getFk_componente() == that.getFk_componente();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFk_evento(), getFk_componente());
    }
}
