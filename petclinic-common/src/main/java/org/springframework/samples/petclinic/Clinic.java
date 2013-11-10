package org.springframework.samples.petclinic;

import org.openspaces.remoting.ExecutorRemotingMethod;
import org.openspaces.remoting.Routing;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * The high-level PetClinic business interface.
 *
 * <p>This is basically a data access object.
 * PetClinic doesn't have a dedicated business facade.
 *
 */
public interface Clinic {

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
    @ExecutorRemotingMethod(broadcast =true, remoteResultReducer = "getVetsReducer")
	Collection<Vet> getVets() throws DataAccessException;

	/**
	 * Retrieve all <code>PetType</code>s from the data store.
	 * @return a <code>Collection</code> of <code>PetType</code>s
	 */
    @ExecutorRemotingMethod(broadcast = false, remoteInvocationAspect= "petTypesAspect")
	Collection<PetType> getPetTypes() throws DataAccessException;

	/**
	 * Retrieve <code>Owner</code>s from the data store by last name,
	 * returning all owners whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a <code>Collection</code> of matching <code>Owner</code>s
	 * (or an empty <code>Collection</code> if none found)
	 */
    @ExecutorRemotingMethod(broadcast =true, remoteResultReducer = "findOwnersReducer")
	Collection<Owner> findOwners(String lastName) throws DataAccessException;

	/**
	 * Retrieve an <code>Owner</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Owner</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
    @ExecutorRemotingMethod(broadcast = false)
	Owner loadOwner(@Routing int id) throws DataAccessException;

	/**
	 * Retrieve a <code>Pet</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Pet</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
    @ExecutorRemotingMethod(broadcast =true, remoteResultReducer = "loadPetResultReducer")
	Pet loadPet(int id) throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or updating it.
	 *
     * @param owner the <code>Owner</code> to save
     * @see BaseEntity#isNewEntity
	 */
    @ExecutorRemotingMethod(broadcast =false, remoteInvocationAspect = "idGeneratingInvocationAspect")
    void storeOwner(@Routing("getId") Owner owner) throws DataAccessException;

	/**
	 * Save a <code>Pet</code> to the data store, either inserting or updating it.
	 *
     *
     * @param pet the <code>Pet</code> to save
     * @see BaseEntity#isNewEntity
	 */
    @ExecutorRemotingMethod(broadcast = false, remoteRoutingHandler = "petRoutingHandler", remoteInvocationAspect = "idGeneratingInvocationAspect")
	void storePet(Pet pet) throws DataAccessException;

	/**
	 * Save a <code>Visit</code> to the data store, either inserting or updating it.
	 *
     *
     * @param visit the <code>Visit</code> to save
     * @see BaseEntity#isNewEntity
	 */
    @ExecutorRemotingMethod(broadcast = false, remoteRoutingHandler = "visitRoutingHandler", remoteInvocationAspect = "idGeneratingInvocationAspect")
	void storeVisit(Visit visit) throws DataAccessException;

	/**
	 * Deletes a <code>Pet</code> from the data store.
	 */
    @ExecutorRemotingMethod(broadcast = false, remoteRoutingHandler = "petRoutingHandler")
	void deletePet(Pet petToDelete) throws DataAccessException;

    @ExecutorRemotingMethod(broadcast =false, remoteInvocationAspect = "idGeneratingInvocationAspect")
    void storeVet(@Routing("getId") Vet vet);
}
