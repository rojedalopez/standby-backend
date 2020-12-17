package co.com.bancolombia.model.clavevalor.gateways;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.clavevalor.GrupoClave;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClaveValorRepository {
    Flux<ClaveValor> buscarTodo();
    Mono<ClaveValor> buscarPorId(int id);
    Flux<ClaveValor> buscarPorFk_GrupoClave(int id_grupoclave);
    Mono<ClaveValor> guardar(ClaveValor evento);
    Mono<ClaveValor> editar(ClaveValor evento);
    Mono<Void> eliminar(ClaveValor evento);
}
