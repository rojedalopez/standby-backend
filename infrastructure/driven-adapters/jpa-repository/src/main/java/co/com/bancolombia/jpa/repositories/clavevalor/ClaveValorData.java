package co.com.bancolombia.jpa.repositories.clavevalor;

import co.com.bancolombia.jpa.repositories.ciclo.CicloData;
import co.com.bancolombia.jpa.repositories.componente.ComponenteData;
import co.com.bancolombia.jpa.repositories.evento.EventoData;
import co.com.bancolombia.model.evento.Evento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "clavevalor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaveValorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_clavevalor;

    @ManyToOne
    @JoinColumn(name = "fk_grupoclave", referencedColumnName = "id_grupoclave")
    public GrupoClaveData fk_grupoclave;

    @Column
    public String clave;

    @Column
    public String valor;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean activo = true;

    @OneToMany
    private List<EventoData> eventoList;

    @OneToMany
    private List<ComponenteData> componenteList;

    @OneToMany
    private List<CicloData> cicloList;
}
