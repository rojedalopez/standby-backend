package co.com.bancolombia.model.clavevalor.gateways;

import co.com.bancolombia.model.clavevalor.GrupoClave;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GrupoClaveRepository {
    Flux<GrupoClave> buscarTodo();
    Mono<GrupoClave> buscarPorId(int id);
    Mono<GrupoClave> guardar(GrupoClave evento);
    Mono<GrupoClave> editar(GrupoClave evento);
    Mono<Void> eliminar(GrupoClave evento);
}
