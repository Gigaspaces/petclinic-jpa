package org.springframework.samples.petclinic;

/**
 * Models a {@link Vet Vet's} specialty (for example, dentistry).
 * 
 */

public enum Specialty {
    RADIOLOGY("Radiology"),DENTISTRY("Dentistry"),SURGERY("Surgery"), DERMATOLOGY("Dermatology");
    private final String name;
    Specialty(String name) {
              this.name = name;
    }
     public String getName() {
        return this.name;
    }
}
