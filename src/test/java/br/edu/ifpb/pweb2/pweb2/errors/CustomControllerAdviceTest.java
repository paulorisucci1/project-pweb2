package br.edu.ifpb.pweb2.pweb2.errors;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundExceptionFactory;
import lombok.AllArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(SpringExtension.class)
public class CustomControllerAdviceTest {

    @InjectMocks
    private CustomControllerAdvice customControllerAdvice;

    @Test
    public void shouldThreatNotFoundException() {
        final var expectedResponseEntity = ResponseEntityFactory.getNotFoundResponseEntity();

        final var actualResponseEntity = customControllerAdvice
                .handleEntityNotFoundException(EntityNotFoundExceptionFactory.getEntityNotFoundException());

            assertThat(actualResponseEntity.getStatusCode()).isEqualTo(expectedResponseEntity.getStatusCode());

    }
}
