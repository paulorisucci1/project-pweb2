package br.edu.ifpb.pweb2.pweb2.errors;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class ErrorResponseTest {

    @Test
    public void shouldCreateDefaultErrorResponseSuccessfully() {
        final var defaultErrorResponse = ErrorResponseFactory.createDefaultErrorResponse();

        assertThat(defaultErrorResponse.getTimestamp()).isInstanceOf(Date.class);
    }

    @Test
    public void shouldCreateCustomizedErrorResponseSuccessfully() {
        final var customizedErrorResponse = ErrorResponseFactory
                .createErrorResponseWithStatusAndMessage(HttpStatus.BAD_REQUEST, "bad request");

        assertSoftly(softly -> {
            softly.assertThat(customizedErrorResponse.getTimestamp()).isInstanceOf(Date.class);
            softly.assertThat(customizedErrorResponse.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            softly.assertThat(customizedErrorResponse.getMessage()).isEqualTo("bad request");
            softly.assertThat(customizedErrorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.name());
        });
    }
}
