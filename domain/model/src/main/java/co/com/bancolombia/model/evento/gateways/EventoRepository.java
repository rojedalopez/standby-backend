package co.com.bancolombia.model.evento.gateways;

import co.com.bancolombia.model.evento.Evento;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EventoRepository {
    Flux<Evento> buscarTodo();
    Mono<Evento> buscarPorId(int id);
    Mono<Evento> guardar(Evento evento);
    Mono<Evento> editar(Evento evento);
    Mono<Void> eliminar(Evento evento);
}
