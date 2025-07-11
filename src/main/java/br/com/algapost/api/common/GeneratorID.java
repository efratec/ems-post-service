package br.com.algapost.api.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public class GeneratorID {

    private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator =
            Generators.timeBasedEpochRandomGenerator();

    private GeneratorID() {}

    public static UUID generateTimeBaseUUID() {
        return timeBasedEpochRandomGenerator.generate();
    }

}
