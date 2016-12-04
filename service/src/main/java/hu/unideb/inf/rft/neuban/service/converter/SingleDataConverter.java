package hu.unideb.inf.rft.neuban.service.converter;


import java.util.Optional;

public interface SingleDataConverter<SOURCE, TARGET> {

    Optional<SOURCE> convertToSource(TARGET target);

    Optional<TARGET> convertToTarget(SOURCE source);
}
