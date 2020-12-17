package co.com.bancolombia.model.evento.gateways;

import co.com.bancolombia.model.evento.Evento;
import co.com.bancolombia.model.evento.EventoComponente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventoComponenteRepository {
    Flux<EventoComponente> buscarPorEvento(int evento);
    Flux<EventoComponente> buscarPorComponente(int componente);
    Mono<EventoComponente> guardar(EventoComponente eventoComponente);
}
