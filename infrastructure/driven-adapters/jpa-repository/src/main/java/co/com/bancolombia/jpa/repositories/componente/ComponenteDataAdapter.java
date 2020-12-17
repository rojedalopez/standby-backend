package co.com.bancolombia.jpa.repositories.componente;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ComponenteDataAdapter  extends CrudRepository<ComponenteData, Integer>, QueryByExampleExecutor<ComponenteData> {
    //@Query(value = "SELECT c FROM componente c WHERE c.nombre LIKE %?1%")
    List<ComponenteData> findByNombreContainsIgnoreCase(String nombre);
}
