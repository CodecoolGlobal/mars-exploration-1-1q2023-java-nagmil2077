package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilder;

import java.util.ArrayList;
import java.util.List;

public class MapElementsGeneratorImpl implements MapElementsGenerator {

    MapElementBuilder mapElementFactory;

    public MapElementsGeneratorImpl(MapElementBuilder mapElementBuilder) {
        this.mapElementFactory = mapElementBuilder;
    }

    @Override
    public Iterable<MapElement> createAll(MapConfiguration mapConfig) {
        List<MapElement> mapElementsList = new ArrayList<>();
        List<MapElementConfiguration> mapElementConfigurations = mapConfig.mapElementConfigurations();

        iterateOnMapElementConfigurations(mapElementsList, mapElementConfigurations);

        return mapElementsList;
    }

    private void iterateOnMapElementConfigurations(List<MapElement> mapElementsList, List<MapElementConfiguration> mapElementConfigurations) {
        for (MapElementConfiguration mapElementConfiguration : mapElementConfigurations) {
            int dimensionGrowth = mapElementConfiguration.dimensionGrowth();
            String name = mapElementConfiguration.name();
            String symbol = mapElementConfiguration.symbol();
            String preferredLocationSymbol = mapElementConfiguration.preferredLocationSymbol();
            List<ElementToSize> elementToSizesList = mapElementConfiguration.elementToSizes();

            iterateOnElementToSizes(mapElementsList, dimensionGrowth, name, symbol, preferredLocationSymbol, elementToSizesList);
        }
    }

    private void iterateOnElementToSizes(
            List<MapElement> mapElementsList,
            int dimensionGrowth,
            String name,
            String symbol,
            String preferredLocationSymbol, List<ElementToSize> elementToSizesList) {
        for (ElementToSize element : elementToSizesList) {
            int size = element.size();
            int elementCount = element.elementCount();

            addBuiltMapElementsToList(mapElementsList, dimensionGrowth, name, symbol, preferredLocationSymbol, size, elementCount);
        }
    }

    private void addBuiltMapElementsToList(List<MapElement> mapElementsList, int dimensionGrowth, String name, String symbol, String preferredLocationSymbol, int size, int elementCount) {
        for (int i = 0; i < elementCount; i++) {
            MapElement mapElement = mapElementFactory.build(size, symbol, name, dimensionGrowth, preferredLocationSymbol);
            mapElementsList.add(mapElement);
        }
    }
}
