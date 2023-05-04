package br.edu.ifpb.pweb2.pweb2.model;

import java.util.List;

public class InstitutionFactory {

    public static Institution createInstitutionWithoutViolations() {
        final var institution = new Institution();

        institution.setIdInstitution(1);
        institution.setPhone("83987654321");
        institution.setAcronym("IFPB");

        return institution;
    }

    public static Institution createInstitutionViolatingAcronymMinimalSize() {
        final var institution = new Institution();

        institution.setAcronym("IF");

        return institution;
    }

    public static Institution createInstitutionViolatingPhoneMinimalSize() {
        final var institution = new Institution();

        institution.setPhone("987654321");

        return institution;
    }

    public static List<Institution> createInstitutionsList() {
        return List.of(createInstitutionWithoutViolations());
    }

    public static Institution createDummyInstitution() {
        final var dummyInstitution = new Institution();

        dummyInstitution.setIdInstitution(1);

        return dummyInstitution;
    }
}
