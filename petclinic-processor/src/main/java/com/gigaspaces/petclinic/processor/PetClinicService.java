package com.gigaspaces.petclinic.processor;

import org.openspaces.remoting.RemotingService;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Yaronpa
 * Date: 1/6/11
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@RemotingService
@Transactional("jpaTransactionManager")
public class PetClinicService implements Clinic {

    @PersistenceContext
    private EntityManager em;

    public PetClinicService() {
    }


    @Transactional(readOnly = true)
    public Collection<Vet> getVets() throws DataAccessException {
        return em.createQuery("SELECT vet FROM org.springframework.samples.petclinic.Vet vet ORDER BY vet.lastName, vet.firstName").getResultList();
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Collection<PetType> getPetTypes() {
        throw new UnsupportedOperationException("this call should not reach the server side");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Collection<Owner> findOwners(String lastName) {
        Query query;
        query = em.createQuery("SELECT owner FROM org.springframework.samples.petclinic.Owner owner WHERE owner.lastName LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public Owner loadOwner(int id) {
        return em.find(Owner.class, id);
    }

    @Transactional(readOnly = true)
    public Pet loadPet(int id) {
        Query query = em.createQuery("SELECT o FROM org.springframework.samples.petclinic.Owner o JOIN o.petsInternal p WHERE p.id = :id");
        query.setParameter("id", id);
        List<Owner> owners = query.getResultList();
        if (!owners.isEmpty()) {
            Owner owner = owners.get(0);
            for (Pet pet : owner.getPets()) {
                if (pet.getId() == id) {
                    return pet;
                }
            }
        }
        return null;
    }

    public void storeOwner(Owner owner) {
        em.merge(owner);
    }

    public void storePet(Pet pet) {
        int ownerId = pet.getOwner().getId();
        Owner owner = em.find(Owner.class, ownerId);
        if (owner.getPets().contains(pet)) {
            // updating an existing pet requires merging
            em.merge(pet);
        } else {
            // new pet
            owner.addPet(pet);
        }
    }

    public void storeVisit(Visit visit) {
        int petId = visit.getPet().getId();
        Query query = em.createQuery("SELECT o FROM org.springframework.samples.petclinic.Owner o JOIN o.petsInternal p WHERE p.id = :id");
        query.setParameter("id", petId);
        Owner owner = (Owner) query.getSingleResult();
        for (Pet pet : owner.getPets()) {
            if (pet.getId() == petId) {
                pet.addVisit(visit);
            }
        }
    }

    public void deletePet(Pet petToDelete) throws DataAccessException {
        Query query = em.createQuery("SELECT o FROM org.springframework.samples.petclinic.Owner o JOIN o.petsInternal p WHERE p.id = :id");
        Integer id = petToDelete.getId();
        query.setParameter("id", id);
        Owner owner = (Owner) query.getSingleResult();
        for (Pet pet : owner.getPets()) {
            if (pet.getId().equals(id)) {
                owner.removePet(id);
            }
        }
        em.merge(owner);
    }

    public void storeVet(Vet vet) {
        em.merge(vet);
    }
}
