package co.com.bancolombia.jpa.repositories.usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UsuarioDataAdapter extends CrudRepository<UsuarioData, String>, QueryByExampleExecutor<UsuarioData> {
}
