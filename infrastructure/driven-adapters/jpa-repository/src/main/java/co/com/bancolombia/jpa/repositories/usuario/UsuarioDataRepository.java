package co.com.bancolombia.jpa.repositories.usuario;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.model.usuario.gateways.UsuarioRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Repository
public class UsuarioDataRepository extends AdapterOperations<Usuario, UsuarioData, String, UsuarioDataAdapter>
        implements UsuarioRepository {
    public UsuarioDataRepository(UsuarioDataAdapter repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Usuario.UsuarioBuilder.class)
                .fk_subdominio(mapper.mapBuilder(d.getFk_subdominio(), ClaveValor.ClaveValorBuilder.class).build())
                .build());
    }

    @Override
    public Flux<Usuario> buscarTodo() {
        return Flux.fromIterable(super.findAll());
    }

    @Override
    public Mono<Usuario> crear(Usuario usuario) {
        return Mono.just(super.save(usuario));
    }

    @Override
    public Mono<Usuario> editar(Usuario usuario) {
        return Mono.just(super.save(usuario));
    }

    @Override
    public Mono<Usuario> bucarPorId(String id_usuario) {
        Usuario _usuario = super.findById(id_usuario);
        if(_usuario != null)
            return Mono.just(_usuario);
        else
            return Mono.empty();
    }
}
