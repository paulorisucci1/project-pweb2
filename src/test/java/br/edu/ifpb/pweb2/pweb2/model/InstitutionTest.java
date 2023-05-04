package br.edu.ifpb.pweb2.pweb2.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InstitutionTest {

    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        final var validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldCreateInstitution() {
        final var institution = InstitutionFactory.createInstitutionWithoutViolations();

        final var violations = validator.validate(institution);

        assertThat(violations.size()).isZero();
    }

    @Test
    public void shouldCreateInstitutionViolatingAcronymMinimalSize() {
        final var institution = InstitutionFactory.createInstitutionViolatingAcronymMinimalSize();

        final var violations = validator.validate(institution);

        assertThat(violations.size()).isOne();
    }

    @Test
    public void shouldCreateInstitutionViolatingPhoneMinimalSize() {
        final var institution = InstitutionFactory.createInstitutionViolatingPhoneMinimalSize();

        final var violations = validator.validate(institution);

        assertThat(violations.size()).isOne();
    }

    @Test
    public void shouldUpdateInstitutionSuccessfully() {
        final var currentInstitution = InstitutionFactory.createDummyInstitution();
        final var updatedInstitution = InstitutionFactory.createInstitutionWithoutViolations();

        currentInstitution.update(updatedInstitution);

        assertThat(currentInstitution).isEqualTo(updatedInstitution);
    }
}
