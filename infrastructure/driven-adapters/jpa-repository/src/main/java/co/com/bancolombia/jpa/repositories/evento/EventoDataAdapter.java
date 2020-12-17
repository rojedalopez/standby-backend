package co.com.bancolombia.jpa.repositories.evento;

import co.com.bancolombia.model.evento.Evento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface EventoDataAdapter extends CrudRepository<EventoData, Integer>, QueryByExampleExecutor<EventoData>
{

}
