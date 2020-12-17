package co.com.bancolombia.model.evento;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.componente.Componente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @NotNull
    private int id_evento;

    private String nota;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date hora;

    @NotEmpty
    @Size(min = 5, max = 20)
    private String usuario;

    @NotNull
    private ClaveValor fk_subdominio;

    @NotEmpty
    @Size(min = 20, max = 1500)
    private String error;

    private String incidente;

    @NotEmpty
    @Size(min = 20, max = 1500)
    private String diagnostico;

    @NotEmpty
    @Size(min = 20, max = 1500)
    private String solucion;

    @NotEmpty
    @Pattern(regexp = "(\\W|^)(Temporal|Definitiva)(\\W|$)", message = "EL campo tiposolucion solo se puede ingresar las palabras Definitiva o Temporal")
    private String tiposolucion;

    @NotNull
    private List<Componente> componentes;
}