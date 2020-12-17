package co.com.bancolombia.model.usuario.gateways;

import co.com.bancolombia.model.usuario.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UsuarioRepository {
    Flux<Usuario> buscarTodo();
    Mono<Usuario> crear(Usuario usuario);
    Mono<Usuario> editar(Usuario usuario);
    Mono<Usuario> bucarPorId(String id_usuario);
}
