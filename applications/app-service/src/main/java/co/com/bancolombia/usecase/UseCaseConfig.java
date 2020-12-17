package co.com.bancolombia.usecase;

import co.com.bancolombia.model.clavevalor.gateways.ClaveValorRepository;
import co.com.bancolombia.model.clavevalor.gateways.GrupoClaveRepository;
import co.com.bancolombia.model.componente.gateways.ComponenteRepository;
import co.com.bancolombia.model.evento.gateways.EventoComponenteRepository;
import co.com.bancolombia.model.evento.gateways.EventoRepository;
import co.com.bancolombia.model.interfaces.AwsRepository;
import co.com.bancolombia.model.usuario.gateways.UsuarioRepository;
import co.com.bancolombia.usecase.clavevalor.ClaveValorUseCase;
import co.com.bancolombia.usecase.componente.ComponenteUseCase;
import co.com.bancolombia.usecase.evento.EventoUseCase;
import co.com.bancolombia.usecase.usuario.UsuarioUseCase;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public EventoUseCase createEventoUseCase(EventoRepository repository, AwsRepository awsRepository, EventoComponenteRepository eventoComponenteRepository){
        return new EventoUseCase(repository, awsRepository, eventoComponenteRepository);
    }

    @Bean
    public ClaveValorUseCase createClaveValorUseCase(ClaveValorRepository claveValorRepository, GrupoClaveRepository grupoClaveRepository){
        return new ClaveValorUseCase(grupoClaveRepository, claveValorRepository);
    }

    @Bean
    public ComponenteUseCase createComponenteUseCase(ComponenteRepository componenteRepository, AwsRepository awsRepository){
        return new ComponenteUseCase(componenteRepository, awsRepository
        );
    }

    @Bean
    public UsuarioUseCase createUsuarioUseCas(UsuarioRepository usuarioRepository){
        return new UsuarioUseCase(usuarioRepository);
    }

    @Bean
    public ObjectMapperImp createObjectMapper() {
        return new ObjectMapperImp();
    }

}
