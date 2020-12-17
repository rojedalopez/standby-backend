package co.com.bancolombia.usecase.evento;

import co.com.bancolombia.model.componente.Componente;
import co.com.bancolombia.model.evento.Evento;
import co.com.bancolombia.model.evento.EventoComponente;
import co.com.bancolombia.model.evento.gateways.EventoComponenteRepository;
import co.com.bancolombia.model.evento.gateways.EventoRepository;
import co.com.bancolombia.model.interfaces.AwsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

@RequiredArgsConstructor
public class EventoUseCase {

    private final EventoRepository repository;
    private final AwsRepository awsRepository;
    private final EventoComponenteRepository ecRepository;

    public Flux<Evento> buscarTodo(){
        return repository.buscarTodo();
    }

    public Flux<Evento> buscarPorComponente(int id_componente){
        return ecRepository.buscarPorComponente(id_componente)
                .map(ev-> ev.getFk_evento());
    }

    public Mono<Evento> buscarPorId(int id_evento) {
        return repository.buscarPorId(id_evento)
                .flatMap(ev -> {
                    ev.setComponentes(new LinkedList<>());
                    return ecRepository.buscarPorEvento(ev.getId_evento())
                        .map(ec -> ec.getFk_componente())
                        .collectList()
                        .map(lista -> {ev.setComponentes(lista); return ev;});
                });


    }
    public Mono<Evento> crear(Evento evento) {
        return repository.guardar(evento)
                .flatMap(ev -> {
                    for(Componente c : evento.getComponentes()){
                        ecRepository.guardar(new EventoComponente(c, ev))
                                .flatMap(Mono::just);
                    }
                    return Mono.just(ev);
                });
    }
    public Mono<Evento> editar(Evento evento) {
        return buscarPorId(evento.getId_evento())
                .flatMap(e -> repository.editar(evento)
                        .flatMap(ev -> {
                            for(Componente c : evento.getComponentes()){
                                ecRepository.guardar(new EventoComponente(c, ev))
                                        .flatMap(Mono::just);
                            }
                            return Mono.just(ev);
                        })
                ).switchIfEmpty(Mono.empty());
    }

    public Mono<String> eliminar(int id) {
        return buscarPorId(id)
                .flatMap(e -> repository.eliminar(e).thenReturn("Ok")).switchIfEmpty(Mono.just("No existe"));
    }

    public Mono<Void> subirArchivo(String nameFile){
        awsRepository.uploadFile(nameFile);
        return Mono.empty().then();
    }

    public String getPathFile(){
        return awsRepository.getPathFile();
    }
}
