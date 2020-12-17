package co.com.bancolombia.jpa.repositories.evento;

import co.com.bancolombia.model.componente.Componente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface EventoComponenteAdapter extends CrudRepository<EventoComponenteData, EventoComponentePK>, QueryByExampleExecutor<EventoComponenteData> {

    @Query("SELECT ec FROM eventocomponente ec WHERE ec.fk_evento.id_evento = ?1 ")
    List<EventoComponenteData> findComponentesByEvento(int evento);

    @Query("SELECT ec FROM eventocomponente ec WHERE ec.fk_componente.id_componente = ?1 ")
    List<EventoComponenteData> findByEventoComponentes(int componente);
}
