package br.com.algapost.api.common;

import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class GenerateSummary {

    public String generateSummary(String body) {
        if (body == null || body.isBlank()) {
            return "";
        }

        return body
                .lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .limit(3)
                .collect(Collectors.joining("\n"));
    }

}
