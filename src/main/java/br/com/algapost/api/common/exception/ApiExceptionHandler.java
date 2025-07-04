package br.com.algapost.api.common.exception;

import br.com.algapost.domain.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String POST_NOT_FOUND = "Post Not Found";
    public static final String ERRORS_NOT_FOUND = "/errors/not-found";

    @ExceptionHandler(PostNotFoundException.class)
    public ProblemDetail handle(PostNotFoundException exception) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(POST_NOT_FOUND);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create(ERRORS_NOT_FOUND));
        return problemDetail;
    }

}
