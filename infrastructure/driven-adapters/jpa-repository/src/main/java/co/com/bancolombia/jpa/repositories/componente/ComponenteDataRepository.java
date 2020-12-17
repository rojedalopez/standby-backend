package co.com.bancolombia.jpa.repositories.componente;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.componente.Ambiente;
import co.com.bancolombia.model.componente.Componente;
import co.com.bancolombia.model.componente.gateways.ComponenteRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ComponenteDataRepository extends AdapterOperations<Componente, ComponenteData, Integer, ComponenteDataAdapter>
implements ComponenteRepository {
    public ComponenteDataRepository(ComponenteDataAdapter repository, ObjectMapper mapper) {
        super(repository, mapper,
                d -> mapper.mapBuilder(d, Componente.ComponenteBuilder.class)
                        .fk_tipocomponente(mapper.mapBuilder(d.getFk_tipocomponente(), ClaveValor.ClaveValorBuilder.class).build())
                        .ambientes(mapper.map(d.getAmbientes(), Ambiente[].class))
                        .build());
    }

    @Override
    public Flux<Componente> buscarTodo() {
        return Flux.fromIterable(super.findAll());
    }

    @Override
    public Mono<Componente> buscarPorId(int id) {
        Componente componente = super.findById(id);
        if(componente!= null)
            return Mono.just(componente);
        else
            return Mono.empty();
    }

    @Override
    public Flux<Componente> buscarPorNombre(String  nombre) {
        return Flux.fromIterable(super.toList(repository.findByNombreContainsIgnoreCase(nombre)));
    }

    @Override
    public Mono<Componente> guardar(Componente componente) {
        return Mono.just(super.save(componente));
    }

    @Override
    public Mono<Componente> editar(Componente componente) {
        return Mono.just(super.save(componente));
    }

    @Override
    public Mono<Void> eliminar(Componente componente) {
        repository.deleteById(componente.getId_componente());
        return Mono.empty().then();
    }
}
