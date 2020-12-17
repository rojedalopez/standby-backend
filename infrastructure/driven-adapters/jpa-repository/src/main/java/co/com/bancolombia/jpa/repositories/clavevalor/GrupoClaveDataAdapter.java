package co.com.bancolombia.jpa.repositories.clavevalor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface GrupoClaveDataAdapter extends CrudRepository<GrupoClaveData, Integer>, QueryByExampleExecutor<GrupoClaveData> {
}
