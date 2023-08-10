package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.service.*;
import com.codecool.marsexploration.configuration.model.*;
import com.codecool.marsexploration.configuration.service.*;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.*;
import com.codecool.marsexploration.mapelements.service.generator.*;
import com.codecool.marsexploration.mapelements.service.placer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    // You can change this to any directory you like
    private static final String WorkDir = "src/main";

    public static void main(String[] args) {
        System.out.println("Mars Exploration Sprint 1");
        MapConfiguration mapConfig = getConfiguration();
        int mapSizeSqrt = (int) Math.sqrt(mapConfig.mapSize());
        Map map = new Map(new String[mapSizeSqrt][mapSizeSqrt]);

        MapConfigurationValidator mapConfigValidator = new MapConfigurationValidatorImpl();
        System.out.println("MAP CONFIGURATION IS VALID: " + mapConfigValidator.validate(mapConfig));

        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();
        CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();

        MapElementBuilder mapElementFactory = new MapElementBuilderImpl(dimensionCalculator, coordinateCalculator);
        MapElementsGenerator mapElementsGenerator = new MapElementsGeneratorImpl(mapElementFactory);
        List<MapElement> mapElements = (List<MapElement>) mapElementsGenerator.createAll(mapConfig);
        System.out.println("Number of elements: " + mapElements.size());
        MapElementPlacer mapElementPlacer = new MapElementPlacerImpl(coordinateCalculator);

        String[][] representation = map.getRepresentation();
        replaceNullWithEmptyStrings(representation);
        MapGenerator mapGenerator = new MapGeneratorImpl(map, mapElementsGenerator, coordinateCalculator, mapElementPlacer);
        Map generatedMap = mapGenerator.generate(mapConfig);
        generatedMap.setSuccessfullyGenerated(true);

        for (String[] rep : representation) {
            System.out.println(Arrays.toString(rep));
        }

        createAndWriteMaps(3, mapGenerator, mapConfig);

        System.out.println("Mars maps successfully generated.");
    }

    private static void replaceNullWithEmptyStrings(String[][] representation) {
        for (String[] row : representation) {
            Arrays.fill(row, " ");
        }
    }

    private static void createAndWriteMaps(int count, MapGenerator mapGenerator, MapConfiguration mapConfig) {
    }

    private static MapConfiguration getConfiguration() {
        final String mountainSymbol = "#";
        final String pitSymbol = "&";
        final String mineralSymbol = "%";
        final String waterSymbol = "*";

        MapElementConfiguration mountainsCfg = new MapElementConfiguration(
                mountainSymbol,
                "mountain",
                List.of(
                        new ElementToSize(2, 20),
                        new ElementToSize(1, 30)
                ),
                3,
                ""
        );

        MapElementConfiguration pitsCfg = new MapElementConfiguration(
                pitSymbol,
                "pits",
                List.of(
                        new ElementToSize(2, 10),
                        new ElementToSize(1, 20)
                ),
                10,
                ""
        );

        MapElementConfiguration mineralsCfg = new MapElementConfiguration(
                mineralSymbol,
                "minerals",
                List.of(new ElementToSize(10, 1)),
                0,
                mountainSymbol
        );

        MapElementConfiguration watersCfg = new MapElementConfiguration(
                waterSymbol,
                "waters",
                List.of(new ElementToSize(10, 1)),
                0,
                pitSymbol
        );

//        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg);
        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg, pitsCfg, mineralsCfg, watersCfg);
        return new MapConfiguration(625, 0.5, elementsCfg);
    }
}

