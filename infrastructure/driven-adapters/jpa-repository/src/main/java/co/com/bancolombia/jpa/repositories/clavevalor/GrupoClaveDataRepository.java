package co.com.bancolombia.jpa.repositories.clavevalor;


import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.clavevalor.GrupoClave;
import co.com.bancolombia.model.clavevalor.gateways.GrupoClaveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class GrupoClaveDataRepository extends AdapterOperations<GrupoClave, GrupoClaveData, Integer, GrupoClaveDataAdapter>
implements GrupoClaveRepository {
    public GrupoClaveDataRepository(GrupoClaveDataAdapter repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, GrupoClave.GrupoClaveBuilder.class).build());
    }

    @Override
    public Flux<GrupoClave> buscarTodo() {
        return Flux.fromIterable(super.findAll());
    }

    @Override
    public Mono<GrupoClave> buscarPorId(int id) {
        GrupoClave grupoClave = super.findById(id);
        if(grupoClave!=null)
            return Mono.just(grupoClave);
        else
            return Mono.empty();
    }

    @Override
    public Mono<GrupoClave> guardar(GrupoClave grupoClave) {
        return Mono.just(super.save(grupoClave));
    }

    @Override
    public Mono<GrupoClave> editar(GrupoClave grupoClave) {
        return Mono.just(super.save(grupoClave));
    }

    @Override
    public Mono<Void> eliminar(GrupoClave grupoClave) {
        repository.deleteById(grupoClave.getId_grupoclave());
        return Mono.empty().then();
    }
}
