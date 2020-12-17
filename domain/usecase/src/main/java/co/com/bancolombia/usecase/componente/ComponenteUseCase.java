package co.com.bancolombia.usecase.componente;

import co.com.bancolombia.model.componente.Componente;
import co.com.bancolombia.model.componente.gateways.ComponenteRepository;
import co.com.bancolombia.model.interfaces.AwsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ComponenteUseCase {
    private final ComponenteRepository repository;
    private final AwsRepository awsRepository;

    public Flux<Componente> buscarTodo(){
        return repository.buscarTodo();
    }
    public Mono<Componente> buscarPorId(int componente) {
        return repository.buscarPorId(componente)
                .switchIfEmpty(Mono.empty());
    }
    public Flux<Componente> buscarPorNombre(String nombre) {
        return repository.buscarPorNombre(nombre);
    }
    public Mono<Componente> crear(Componente componente) {
         return repository.guardar(componente);
    }
    public Mono<Componente> editar(Componente componente) {
        return repository.buscarPorId(componente.getId_componente())
                .flatMap(e -> repository.editar(componente))
                .switchIfEmpty(Mono.empty());
    }
    public Mono<String> eliminar(int id) {
        return repository.buscarPorId(id)
                .flatMap(e -> {
                    return repository.eliminar(e).thenReturn("Ok");
                }).switchIfEmpty(Mono.just("No existe"));
    }
    public Mono<Void> subirArchivo(String nameFile){
        awsRepository.uploadFile(nameFile);
        return Mono.empty().then();
    }

    public String getPathFile(){
        return awsRepository.getPathFile();
    }
}
