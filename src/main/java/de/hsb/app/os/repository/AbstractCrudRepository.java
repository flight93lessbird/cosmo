package de.hsb.app.os.repository;

import de.hsb.app.os.interfaces.CrudRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudRepository<T> implements CrudRepository<T> {

    @Resource
    protected DataModel<T> entityList;

    protected T selectedEntity;

    @PersistenceContext
    protected EntityManager em;

    @Resource
    protected UserTransaction utx;

//    protected Logger logger = LoggerFactory.getLogger(AbstractCrudRepository.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> findById(final int id) {
        final T entity = this.em.find(this.getRepositoryClass(), id);
        if (entity != null) {
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    
    public List<T> findAll() {
        return this.em.createQuery(this.getQueryCommand()).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(T entity) {
        try {
            this.utx.begin();
            entity = this.em.merge(entity);
            this.em.persist(entity);
            this.utx.commit();
            return true;
        } catch (final NotSupportedException | SystemException | SecurityException | IllegalStateException |
                RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
//            this.logger.error("Speichern fehlgeschlagen -> ", e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete() {
        try {
            this.selectedEntity = this.entityList.getRowData();
            this.utx.begin();
            this.selectedEntity = this.em.merge(this.selectedEntity);
            this.em.remove(this.selectedEntity);
            this.entityList.setWrappedData(this.em.createNamedQuery(this.getSelect()).getResultList());
            this.utx.commit();
            return true;
        } catch (final NotSupportedException | SystemException | SecurityException | IllegalStateException |
                RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
//            this.logger.error("Löschen fehlgeschlagen -> ", e);
            return false;
        }
    }

    /**
     * Liefert die {@link Class<T>} zurueck.
     *
     * @return {@link Class<T>}
     */

    protected abstract Class<T> getRepositoryClass();

    /**
     * Liefert den QueryCommand. Sollte die Form "Select v from {@link T} v" haben.
     *
     * @return "Select v from {@link T} v"
     */
 
    protected abstract String getQueryCommand();

    /**
     * Liefert ein {@link String} zurueck. Der {@link String} sollte die Form Select{@link T} haben.
     *
     * @return Select{@link T}
     */
 
    protected abstract String getSelect();

    /**
     * Prueft ob die EntityList gesetzt wurde und initialisiert sie wenn nicht.
     */
    protected void checkEntityList() {
        if (this.entityList == null) {
            this.entityList = new ListDataModel<>();
        }
    }

    /**
     * Getter fuer {@link DataModel<T>}.
     *
     * @return {@link DataModel<T>}
     */
    public DataModel<T> getEntityList() {
        this.entityList = new ListDataModel<>(this.findAll());
        return this.entityList;
    }

    /**
     * Setter fuer {@link DataModel<T>}
     *
     * @param entityList {@link DataModel<T>}
     */
    public void setEntityList(final DataModel<T> entityList) {
        this.entityList = entityList;
    }

    /**
     * Getter fuer {@link T}
     */
    public T getSelectedEntity() {
        return this.selectedEntity;
    }

    /**
     * Setter fuer {@link T}
     *
     * @param selectedEntity {@link T}
     */
    public void setSelectedEntity(final T selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    /**
     * Soll die Warnung "Unchecked cast" loesen.
     *
     * @param var Object
     * @return
     */
    protected abstract List<T> uncheckedSolver(Object var);

}
