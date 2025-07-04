package br.com.algapost.api.common.exception;

import br.com.algapost.domain.model.Post;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private PostNotFoundException(String message) {
        super(message);
    }

    public static PostNotFoundException throwPostNotFoundException(String message) {
        throw new PostNotFoundException(message);
    }

}
