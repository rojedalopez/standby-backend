package co.com.bancolombia.jpa.repositories.evento;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.componente.Componente;
import co.com.bancolombia.model.evento.Evento;
import co.com.bancolombia.model.evento.EventoComponente;
import co.com.bancolombia.model.evento.gateways.EventoComponenteRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class EventoComponenteDataRepository extends AdapterOperations<EventoComponente, EventoComponenteData, EventoComponentePK, EventoComponenteAdapter>
implements EventoComponenteRepository {

    public EventoComponenteDataRepository(EventoComponenteAdapter repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, EventoComponente.EventoComponenteBuilder.class)
                .fk_evento(mapper.mapBuilder(d.getFk_evento(), Evento.EventoBuilder.class).build())
                .fk_componente(mapper.mapBuilder(d.getFk_componente(), Componente.ComponenteBuilder.class).build())
                        .build()
                );
    }

    @Override
    public Flux<EventoComponente> buscarPorEvento(int evento) {
        return Flux.fromIterable(toList(repository.findComponentesByEvento(evento)));
    }

    @Override
    public Flux<EventoComponente> buscarPorComponente(int componente) {
        return Flux.fromIterable(toList(repository.findByEventoComponentes(componente)));
    }

    @Override
    public Mono<EventoComponente> guardar(EventoComponente eventoComponente) {
        return Mono.just(super.save(eventoComponente));
    }
}
