package br.com.algapost.domain.service;

import br.com.algapost.api.common.GeneratorID;
import br.com.algapost.api.common.mappers.PostMapper;
import br.com.algapost.api.model.PostMessage;
import br.com.algapost.api.model.PostOutput;
import br.com.algapost.api.model.PostResult;
import br.com.algapost.api.model.PostSummaryOutput;
import br.com.algapost.domain.model.Post;
import br.com.algapost.domain.model.PostId;
import br.com.algapost.domain.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import static br.com.algapost.api.common.exception.PostNotFoundException.throwPostNotFoundException;
import static br.com.algapost.infrastructure.rabbitmq.RabbitMQConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private static final PostMapper postMapper = PostMapper.INSTANCE;

    private final RabbitTemplate rabbitTemplate;
    private final PostRepository postRepository;

    @Transactional
    public PostOutput created(Post post) {
        post.setId(PostId.of(GeneratorID.generateTimeBaseUUID()));
        post.setRegisteredAt(OffsetDateTime.now());
        postRepository.saveAndFlush(post);

        log.info("Post Created: {}", post.getId());

        var payload = new PostMessage(post.getId().getValue(), post.getBody());

        log.info(String.valueOf(payload));

        rabbitTemplate.convertAndSend(POST_EXCHANGE, POST_ROUTING_KEY, payload);

        return postMapper.toOutput(post);
    }

    @Transactional
    public void updated(PostResult postResult) {
        var post = postRepository.findById(PostId.of(postResult.postId()))
                .orElseThrow(() -> throwPostNotFoundException("Nao Encontrado nenhum post"));
        post.setWordCount(postResult.wordCount());
        post.setCalculatedValue(postResult.calculatedValue());
        postRepository.save(post);
        log.info("Post Updated: {}", post.getId());
    }

    public PostOutput findById(PostId id) {
        return postRepository.findById(id).map(postMapper::toOutput)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Page<PostSummaryOutput> findAllSummarized(@PageableDefault Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toSummaryOutput);
    }

}
