package br.com.algapost.infrastructure.rabbitmq;

import br.com.algapost.api.model.PostResult;
import br.com.algapost.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

    private final PostService postService;

    @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.RESULT_QUEUE)
    public void handle(@Payload PostResult postResult) {
        postService.updated(postResult);
    }

}
