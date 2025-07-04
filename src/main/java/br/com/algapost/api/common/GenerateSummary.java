package br.com.algapost.api.common;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class GenerateSummary {

    public String generateSummary(String body) {
        if (body == null || body.isBlank()) {
            return "";
        }

        var sentences = body
                .replaceAll("\\s+", " ")
                .trim()
                .split("(?<=[.!?])\\s+");

        return Arrays.stream(sentences)
                .limit(3)
                .map(String::trim)
                .collect(Collectors.joining(" "));
    }

}
