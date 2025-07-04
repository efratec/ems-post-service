package br.com.algapost.api.model;

import java.util.UUID;

public record PostMessage(UUID postId, String postBody) {
}
