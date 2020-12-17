package co.com.bancolombia.jpa.repositories.clavevalor;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.clavevalor.GrupoClave;
import co.com.bancolombia.model.clavevalor.gateways.ClaveValorRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClaveValorDataRepository extends AdapterOperations<ClaveValor, ClaveValorData, Integer, ClaveValorDataAdapter>
implements ClaveValorRepository {

    public ClaveValorDataRepository(ClaveValorDataAdapter repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, ClaveValor.ClaveValorBuilder.class).fk_grupoclave(mapper.mapBuilder(d.getFk_grupoclave(), GrupoClave.GrupoClaveBuilder.class).build()).build());
    }

    @Override
    public Flux<ClaveValor> buscarTodo() {
        return Flux.fromIterable(super.findAll());
    }

    @Override
    public Mono<ClaveValor> buscarPorId(int id) {
        ClaveValor claveValor = super.findById(id);
        if(claveValor!=null)
            return Mono.just(claveValor);
        else
            return Mono.empty();
    }

    @Override
    public Flux<ClaveValor> buscarPorFk_GrupoClave(int id_grupoclave) {
        return Flux.fromIterable(
           super.toList(repository.searchByFk_Grupoclave(id_grupoclave))
        );
    }

    @Override
    public Mono<ClaveValor> guardar(ClaveValor claveValor) {
        return Mono.just(super.save(claveValor));
    }

    @Override
    public Mono<ClaveValor> editar(ClaveValor claveValor) {
        return Mono.just(super.save(claveValor));
    }

    @Override
    public Mono<Void> eliminar(ClaveValor claveValor) {
        repository.deleteById(claveValor.getId_clavevalor());
        return Mono.empty().then();
    }
}
