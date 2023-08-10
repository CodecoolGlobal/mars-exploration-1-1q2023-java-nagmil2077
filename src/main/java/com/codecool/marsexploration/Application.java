package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.*;
import com.codecool.marsexploration.configuration.model.*;
import com.codecool.marsexploration.configuration.service.*;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.*;
import com.codecool.marsexploration.mapelements.service.generator.*;
import com.codecool.marsexploration.mapelements.service.placer.*;
import com.codecool.marsexploration.output.service.MapFileWriter;
import com.codecool.marsexploration.output.service.MapFileWriterImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    // You can change this to any directory you like
    private static final String WorkDir = "src/main";

    private static final String FileDir = "src/main/resources";

    public static void main(String[] args) {
        System.out.println("Mars Exploration Sprint 1");
        MapConfiguration mapConfig = getConfiguration();
        int mapSizeSqrt = (int) Math.sqrt(mapConfig.mapSize());
        MapConfigurationValidator mapConfigValidator = new MapConfigurationValidatorImpl();
        System.out.println("MAP CONFIGURATION IS VALID: " + mapConfigValidator.validate(mapConfig));

        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();
        CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();

        MapElementBuilder mapElementFactory = new MapElementBuilderImpl(dimensionCalculator, coordinateCalculator);
        MapElementsGenerator mapElementsGenerator = new MapElementsGeneratorImpl(mapElementFactory);

        // //
        List<MapElement> mapElements =  mapElementsGenerator.createAll(mapConfig);
        System.out.println("Number of elements: " + mapElements.size());

        MapElementPlacer mapElementPlacer = new MapElementPlacerImpl(coordinateCalculator);

        // //
        String[][] mapRepresentation = new String[mapSizeSqrt][mapSizeSqrt];
        replaceNullWithEmptyStrings(mapRepresentation);

        Map map = new Map(mapRepresentation);
        MapGenerator mapGenerator = new MapGeneratorImpl(map, mapElementsGenerator, coordinateCalculator, mapElementPlacer);
        Map generateMap = mapGenerator.generate(mapConfig);
        generateMap.setSuccessfullyGenerated(true);
        String[][] representation = generateMap.getRepresentation();

        MapFileWriter fileWriter = new MapFileWriterImpl();
        fileWriter.writeMapFile(generateMap, FileDir);

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
        System.out.println(Arrays.deepToString(representation));
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

        MapElementConfiguration pitsCfg = new MapElementConfiguration( //TODO preferredlocation symbol
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
                "#"
        );

        MapElementConfiguration watersCfg = new MapElementConfiguration(
                waterSymbol,
                "waters",
                List.of(new ElementToSize(10, 1)),
                0,
                "&"
        );

        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg, pitsCfg, mineralsCfg, watersCfg);
        return new MapConfiguration(1000, 0.5, elementsCfg);
    }
}

