package br.com.algapost.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PostId implements Serializable {

    @Column(name = "id", nullable = false, updatable = false)
    private UUID value;

    private PostId(UUID value) {
        this.value = Objects.requireNonNull(value);
    }

    public static PostId of(UUID value) {
        return new PostId(value);
    }

    public static PostId of(String value) {
        return new PostId(UUID.fromString(value));
    }

}
