package co.com.bancolombia.usecase.clavevalor;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.clavevalor.GrupoClave;
import co.com.bancolombia.model.clavevalor.gateways.ClaveValorRepository;
import co.com.bancolombia.model.clavevalor.gateways.GrupoClaveRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClaveValorUseCase {
    private final GrupoClaveRepository grupoClaveRepository;
    private final ClaveValorRepository claveValorRepository;

    public Flux<GrupoClave> buscarTodoGrupoClave(){
        return grupoClaveRepository.buscarTodo();
    }
    public Mono<GrupoClave> buscarPorIdGrupoClave(int id) {
        return grupoClaveRepository.buscarPorId(id);
    }
    public Mono<GrupoClave> crearGrupoClave(GrupoClave evento) {
        return grupoClaveRepository.guardar(evento);
    }
    public Mono<GrupoClave> editarGrupoClave(GrupoClave evento) {
        return grupoClaveRepository.editar(evento);
    }
    public Mono<String> eliminarGrupoClave(int id) {
        return buscarPorIdGrupoClave(id)
                .flatMap(e -> {
                    return grupoClaveRepository.eliminar(e).thenReturn("Ok");
                }).switchIfEmpty(Mono.just("No existe"));
    }

    public Flux<ClaveValor> buscarTodoClaveValor(){
        return claveValorRepository.buscarTodo();
    }
    public Flux<ClaveValor> buscarPorGrupoClaveClaveValor(int id){
        return claveValorRepository.buscarPorFk_GrupoClave(id);
    }
    public Mono<ClaveValor> buscarPorIdClaveValor(int id) {
        return claveValorRepository.buscarPorId(id);
    }
    public Mono<ClaveValor> crearClaveValor(ClaveValor evento) {
        return claveValorRepository.guardar(evento);
    }
    public Mono<ClaveValor> editarClaveValor(ClaveValor evento) {
        return claveValorRepository.editar(evento);
    }
    public Mono<String> eliminarClaveValor(int id) {
        return buscarPorIdClaveValor(id)
                .flatMap(e -> {
                    return claveValorRepository.eliminar(e).thenReturn("Ok");
                }).switchIfEmpty(Mono.just("No existe"));
    }

}
