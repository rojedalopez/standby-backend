package co.com.bancolombia.api;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.clavevalor.GrupoClave;
import co.com.bancolombia.usecase.clavevalor.ClaveValorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/clavevalor", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClaveValorController {
    private final ClaveValorUseCase useCase;


    @GetMapping
    public Flux<ClaveValor> get(){
        return useCase.buscarTodoClaveValor();
    }

    @GetMapping("/{id}")
    public Mono<ClaveValor> getById(@PathVariable int id){
        return useCase.buscarPorIdClaveValor(id);
    }

    @PostMapping
    public Mono<ClaveValor> post(@RequestBody ClaveValor claveValor){
        return  useCase.crearClaveValor(claveValor);
    }

    @PutMapping
    public Mono<ClaveValor> put(@RequestBody ClaveValor claveValor){
        return  useCase.editarClaveValor(claveValor);
    }

    @DeleteMapping
    public Mono<ClaveValor> delete(@RequestBody ClaveValor claveValor){
        return useCase.editarClaveValor(claveValor);
    }
}
