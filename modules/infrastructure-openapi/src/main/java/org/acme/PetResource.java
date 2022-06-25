package org.acme;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.openapi.quarkus.openapi_yml.api.PetsApi;
import org.openapi.quarkus.openapi_yml.model.Pet;

public class PetResource implements PetsApi {

    @Override
    public void createPets() {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Pet> listPets(Integer limit) {
        // TODO Auto-generated method stub
        var pet = new Pet();
        pet.setId(1000L);
        return List.of(pet);
    }

    @Override
    public Pet showPetById(String petId) {
        // TODO Auto-generated method stub
        return null;
    }

}