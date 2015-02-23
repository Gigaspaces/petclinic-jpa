package org.springframework.samples.petclinic.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.samples.petclinic.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/8/11
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyDataCreator {

    private Clinic clinic;
    private org.springframework.core.io.Resource dataFileResource;
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");

    public DummyDataCreator() {}

    @Resource
    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public void setDataFileResource(org.springframework.core.io.Resource dataFileResource) {
        this.dataFileResource = dataFileResource;
    }

    public void createDummyData() {
        try {
            List<BaseEntity> loadedEntities = loadEntitiesFromFile();
            for (BaseEntity loadedEntity : loadedEntities) {
                if (loadedEntity instanceof Owner) {
                    clinic.storeOwner((Owner) loadedEntity);
                } else if(loadedEntity instanceof Vet) {
                    clinic.storeVet((Vet)loadedEntity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<BaseEntity> loadEntitiesFromFile() throws Exception {
        List<BaseEntity> entities = new LinkedList<BaseEntity>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(dataFileResource.getInputStream());
        JsonNode vetsNode = rootNode.get("vets");
        Iterator<JsonNode> vetNodesIterator = vetsNode.iterator();
        while (vetNodesIterator.hasNext()) {
            JsonNode vetNode = vetNodesIterator.next();
            Vet vet = new Vet();
            vet.setFirstName(vetNode.get("firstName").asText());
            vet.setLastName(vetNode.get("lastName").asText());
            Iterator<JsonNode> specialtiesNode = vetNode.get("specialties").iterator();
            while (specialtiesNode.hasNext()) {
                Specialty specialty = Specialty.valueOf(specialtiesNode.next().asText());
                vet.addSpecialty(specialty);
            }
            entities.add(vet);
        }

        JsonNode ownersNode = rootNode.get("owners");
        Iterator<JsonNode> ownerNodesIterator = ownersNode.iterator();
        while (ownerNodesIterator.hasNext()) {
            JsonNode ownerNode = ownerNodesIterator.next();
            Owner owner = new Owner();
            owner.setId(ownerNode.get("id").asInt());
            owner.setFirstName(ownerNode.get("firstName").asText());
            owner.setLastName(ownerNode.get("lastName").asText());
            owner.setAddress(ownerNode.get("address").asText());
            owner.setCity(ownerNode.get("city").asText());
            owner.setTelephone(ownerNode.get("telephone").asText());
            Iterator<JsonNode> petNodes= ownerNode.get("pets").iterator();
            while (petNodes.hasNext()) {
                JsonNode petNode =  petNodes.next();
                Pet pet = new Pet();
                pet.setId(petNode.get("id").asInt());
                pet.setName(petNode.get("name").asText());
                pet.setType(PetType.valueOf(petNode.get("type").asText()));
                pet.setBirthDate(dateFormat.parse(petNode.get("birthDate").asText()));
                owner.addPet(pet);
            }
            entities.add(owner);
        }
        return entities;
    }

    public static void main(String[] args) throws Exception {
        DummyDataCreator creator = new DummyDataCreator();
        creator.setDataFileResource(new ClassPathResource("META-INF/dummyData.json"));
        System.out.println(creator.loadEntitiesFromFile());

    }

}
