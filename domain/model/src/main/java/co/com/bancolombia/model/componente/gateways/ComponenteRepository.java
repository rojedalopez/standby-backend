package co.com.bancolombia.model.componente.gateways;

import co.com.bancolombia.model.componente.Componente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ComponenteRepository {
    Flux<Componente> buscarTodo();
    Flux<Componente> buscarPorNombre(String nombre);
    Mono<Componente> buscarPorId(int id);
    Mono<Componente> guardar(Componente componente);
    Mono<Componente> editar(Componente componente);
    Mono<Void> eliminar(Componente componente);
}
