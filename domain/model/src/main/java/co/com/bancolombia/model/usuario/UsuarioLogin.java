package co.com.bancolombia.model.usuario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UsuarioLogin {
    private String id_usuario;
    private String password;
}
