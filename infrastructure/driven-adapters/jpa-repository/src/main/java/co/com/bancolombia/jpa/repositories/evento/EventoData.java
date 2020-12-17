package co.com.bancolombia.jpa.repositories.evento;

import co.com.bancolombia.jpa.repositories.clavevalor.ClaveValorData;
import co.com.bancolombia.model.clavevalor.ClaveValor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "evento")
@NoArgsConstructor
public class EventoData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_evento;

    @Column
    private String nota;

    @Column
    @CreationTimestamp
    private Date hora;

    @Column
    private String usuario;

    @ManyToOne
    @JoinColumn(name = "fk_subdominio", referencedColumnName = "id_clavevalor")
    private ClaveValorData fk_subdominio;

    @Column
    private String error;

    @Column
    private String incidente;

    @Column
    private String diagnostico;

    @Column
    private String solucion;

    @Column
    private String tiposolucion;

    @OneToMany(mappedBy = "fk_evento")
    private Set<EventoComponenteData> eventoComponente = new HashSet<>();
}
