package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.service.*;
import com.codecool.marsexploration.configuration.model.*;
import com.codecool.marsexploration.configuration.service.*;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.*;
import com.codecool.marsexploration.mapelements.service.generator.*;
import com.codecool.marsexploration.mapelements.service.placer.*;

import java.util.List;

public class Application {
    // You can change this to any directory you like
    private static final String WorkDir = "src/main";

    public static void main(String[] args) {
        System.out.println("Mars Exploration Sprint 1");
        MapConfiguration mapConfig = getConfiguration();

        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();
        CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();

        MapElementBuilder mapElementFactory = new MapElementBuilderImpl();

        MapElementsGenerator mapElementsGenerator = new MapElementsGeneratorImpl();

        MapConfigurationValidator mapConfigValidator = new MapConfigurationValidatorImpl();
        MapElementPlacer mapElementPlacer = new MapElementPlacerImpl();

        MapGenerator mapGenerator = new MapGeneratorImpl();

        createAndWriteMaps(3, mapGenerator, mapConfig);

        System.out.println("Mars maps successfully generated.");
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

        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg);
        return new MapConfiguration(1000, 0.5, elementsCfg);
    }
}

