package co.com.bancolombia.usecase.usuario;

import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.model.usuario.UsuarioLogin;
import co.com.bancolombia.model.usuario.gateways.UsuarioRepository;
import co.com.bancolombia.usecase.util.SecurityUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class UsuarioUseCase {
    private final UsuarioRepository repository;

    public Flux<Usuario> buscarTodo(){
        return repository.buscarTodo();
    }

    public Mono<Usuario> buscarPorId(String id_usuario){
        return repository.bucarPorId(id_usuario)
                .map(usuario -> {
                    usuario.setPassword("");
                    usuario.setHash("");
                    return usuario;
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Usuario> crear(Usuario usuario){
        return SecurityUseCase.getInstance().getPasswordSecured(usuario)
                .flatMap(u -> repository.crear(usuario))
                .map(u -> {
                    u.setPassword("");
                    u.setHash("");
                    return u;
                })
                .onErrorResume(e -> Mono.error(e));
    }

    public Mono<Map<String, Object>> actualizar(Usuario _usuario){
        Map<String, Object> respuesta = new HashMap<>();
        return repository.bucarPorId(_usuario.getId_usuario())
                .map(usuario -> {
                    usuario.setNombre(_usuario.getNombre());
                    usuario.setApellido(_usuario.getApellido());
                    usuario.setFecha_nacimiento(_usuario.getFecha_nacimiento());
                    usuario.setCelular(_usuario.getCelular());
                    usuario.setCelularcon(_usuario.getCelularcon());
                    usuario.setContacto(_usuario.getContacto());
                    usuario.setDireccion(_usuario.getDireccion());
                    usuario.setActualizar(false);
                    return usuario;
                })
                .flatMap(usuario -> {

                    if(!_usuario.getPassword().isEmpty()){
                        usuario.setBienvenido(false);
                        usuario.setPassword(_usuario.getPassword());
                        return SecurityUseCase.getInstance().getPasswordSecured(usuario)
                                .flatMap(repository::editar)
                                .map(u -> {
                                    u.setPassword("");
                                    u.setHash("");
                                    respuesta.put("usuario", u);
                                    respuesta.put("token", SecurityUseCase.getInstance().generateToken(u));
                                    return respuesta;
                                });
                    }else{
                        return repository.editar(usuario)
                        .map(u -> {
                            u.setPassword("");
                            u.setHash("");
                            respuesta.put("usuario", u);
                            respuesta.put("token", SecurityUseCase.getInstance().generateToken(u));
                            return respuesta;
                        });
                    }
                });
    }

    public Mono<Map<String, Object>> editar(Usuario _usuario){
        Map<String, Object> respuesta = new HashMap<>();
        return repository.bucarPorId(_usuario.getId_usuario())
                .map(usuario -> {
                    usuario.setActualizar(false);
                    usuario.setNombre(_usuario.getNombre());
                    usuario.setApellido(_usuario.getApellido());
                    usuario.setNivel(_usuario.getNivel());
                    usuario.setFecha_nacimiento(_usuario.getFecha_nacimiento());
                    usuario.setActivo(_usuario.isActivo());
                    usuario.setCelular(_usuario.getCelular());
                    usuario.setCelularcon(_usuario.getCelularcon());
                    usuario.setContacto(_usuario.getContacto());
                    usuario.setDireccion(_usuario.getDireccion());
                    usuario.setFk_subdominio(_usuario.getFk_subdominio());
                    return usuario;
                })
                .flatMap(repository::editar)
                .map(u -> {
                    u.setPassword("");
                    u.setHash("");
                    respuesta.put("usuario", u);
                    return respuesta;
                });
    }

    public Mono<Map<String, Object>> login(UsuarioLogin usuarioLogin) {
        Map<String, Object> respuesta = new HashMap<>();
        return repository.bucarPorId(usuarioLogin.getId_usuario())
                .flatMap(usuario -> {
                    return SecurityUseCase.getInstance().getPasswordSecured(usuario.getHash(), usuarioLogin.getPassword())
                            .flatMap(passEnc -> {
                                if(usuario.isActivo() && usuario.getPassword().equals(passEnc))
                                    return Mono.just(usuario);
                                else
                                    return Mono.empty();
                            }).map(u -> {
                                u.setPassword(null);
                                u.setHash(null);
                                respuesta.put("usuario", u);
                                respuesta.put("token", SecurityUseCase.getInstance().generateToken(u));
                                return respuesta;
                            });
                }).switchIfEmpty(Mono.empty());
    }
}
