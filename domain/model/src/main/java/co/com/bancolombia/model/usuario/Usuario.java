package co.com.bancolombia.model.usuario;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Usuario {
    @NotEmpty
    private String id_usuario;

    private String password;

    private String v_password;

    private String hash;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    private String direccion;

    private String celular;

    private String contacto;

    private String celularcon;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_nacimiento;

    private int nivel;

    private ClaveValor fk_subdominio;

    @Builder.Default
    private boolean activo = true;

    @Builder.Default
    private boolean bienvenido = true;

    @Builder.Default
    private boolean actualizar = true;
}
