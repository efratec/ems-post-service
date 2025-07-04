package br.com.algapost.api.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PostInput(@NotBlank String title,
                        @NotBlank @Length(max = 255) String body,
                        @NotBlank String author) {
}
