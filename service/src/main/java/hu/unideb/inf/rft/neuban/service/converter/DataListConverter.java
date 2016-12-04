package hu.unideb.inf.rft.neuban.service.converter;


import java.util.List;

public interface DataListConverter<SOURCE, TARGET> {

    List<SOURCE> convertToSources(List<TARGET> targets);

    List<TARGET> convertToTargets(List<SOURCE> sources);
}
