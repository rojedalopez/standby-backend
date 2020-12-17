package co.com.bancolombia.jpa.repositories.clavevalor;

import co.com.bancolombia.model.clavevalor.GrupoClave;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ClaveValorDataAdapter extends CrudRepository<ClaveValorData, Integer>, QueryByExampleExecutor<ClaveValorData> {
    @Query("SELECT c FROM clavevalor c WHERE c.fk_grupoclave.id_grupoclave = :fk_grupoclave")
    List<ClaveValorData> searchByFk_Grupoclave(@Param("fk_grupoclave") int fk_grupoclave);
}
