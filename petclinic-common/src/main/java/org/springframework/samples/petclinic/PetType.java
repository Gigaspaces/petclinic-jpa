package org.springframework.samples.petclinic;

public enum PetType {

    LION("Lion"),
    ELEPHANT("Elephant"),
    MONKEY("Monkey"),
    ZEBRA("Zebra"),
    HIPPO("Hippo"),
    GIRAFFE("Giraffe");

    private final String name;

    PetType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
