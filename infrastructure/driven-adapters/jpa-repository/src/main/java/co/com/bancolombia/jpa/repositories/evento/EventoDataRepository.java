package co.com.bancolombia.jpa.repositories.evento;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.evento.Evento;
import co.com.bancolombia.model.evento.gateways.EventoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

@Repository
public class EventoDataRepository extends AdapterOperations<Evento, EventoData, Integer, EventoDataAdapter>
        implements EventoRepository {

    public EventoDataRepository(EventoDataAdapter repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Evento.EventoBuilder.class).fk_subdominio(mapper.mapBuilder(d.getFk_subdominio(), ClaveValor.ClaveValorBuilder.class).build()).build());
    }

    @Override
    public Flux<Evento> buscarTodo() {
        return Flux.fromIterable(super.findAll());
    }

    @Override
    public Mono<Evento> buscarPorId(int id) {
        Evento evento = super.findById(id);
        if(evento != null)
            return Mono.just(super.findById(id));
        else
            return Mono.empty();
    }

    @Override
    public Mono<Evento> guardar(Evento evento) {
        return Mono.just(super.save(evento));
    }

    @Override
    public Mono<Evento> editar(Evento evento) {
        return Mono.just(super.save(evento));
    }

    @Override
    public Mono<Void> eliminar(Evento evento) {
        repository.deleteById(evento.getId_evento());
        return Mono.empty().then();
    }
}
