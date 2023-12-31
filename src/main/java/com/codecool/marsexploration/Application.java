package com.codecool.marsexploration;

import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.CoordinateCalculatorImpl;
import com.codecool.marsexploration.calculators.service.DimensionCalculator;
import com.codecool.marsexploration.calculators.service.DimensionCalculatorImpl;
import com.codecool.marsexploration.configuration.model.ElementToSize;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.configuration.model.MapElementConfiguration;
import com.codecool.marsexploration.configuration.service.MapConfigurationValidator;
import com.codecool.marsexploration.configuration.service.MapConfigurationValidatorImpl;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilder;
import com.codecool.marsexploration.mapelements.service.builder.MapElementBuilderImpl;
import com.codecool.marsexploration.mapelements.service.generator.MapElementsGenerator;
import com.codecool.marsexploration.mapelements.service.generator.MapElementsGeneratorImpl;
import com.codecool.marsexploration.mapelements.service.generator.MapGenerator;
import com.codecool.marsexploration.mapelements.service.generator.MapGeneratorImpl;
import com.codecool.marsexploration.mapelements.service.placer.MapElementPlacer;
import com.codecool.marsexploration.mapelements.service.placer.MapElementPlacerImpl;
import com.codecool.marsexploration.output.service.MapFileWriter;
import com.codecool.marsexploration.output.service.MapFileWriterImpl;

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
        Map map = new Map(new String[mapSizeSqrt][mapSizeSqrt]);

        MapConfigurationValidator mapConfigValidator = new MapConfigurationValidatorImpl();
        System.out.println("MAP CONFIGURATION IS VALID: " + mapConfigValidator.validate(mapConfig));
        DimensionCalculator dimensionCalculator = new DimensionCalculatorImpl();
        CoordinateCalculator coordinateCalculator = new CoordinateCalculatorImpl();
        MapElementBuilder mapElementFactory = new MapElementBuilderImpl(dimensionCalculator, coordinateCalculator);
        MapElementsGenerator mapElementsGenerator = new MapElementsGeneratorImpl(mapElementFactory);
        List<MapElement> mapElements = (List<MapElement>) mapElementsGenerator.createAll(mapConfig);
        System.out.println("Number of elements: " + mapElements.size()); // EDDIG FIXEN JÓ
        MapElementPlacer mapElementPlacer = new MapElementPlacerImpl();

        String[][] representation = map.getRepresentation();
        replaceNullWithEmptyStrings(representation);
        int counter = countEmptyCells(representation);

        MapGenerator mapGenerator = new MapGeneratorImpl(map, mapElementsGenerator, coordinateCalculator, mapElementPlacer);
        Map generatedMap = mapGenerator.generate(mapConfig);
        generatedMap.setSuccessfullyGenerated(true);

        MapFileWriter fileWriter = new MapFileWriterImpl();
        fileWriter.writeMapFile(generatedMap, FileDir);

        printMapToConsole(representation);

        int counter2 = countTakenElementsByCell(representation);
        printInfoToConsole(counter, counter2);

        createAndWriteMaps(1, mapGenerator, mapConfig);

        System.out.println("Mars maps successfully generated.");
    }

    private static void printMapToConsole(String[][] representation) {
        for (String[] rep : representation) {
            System.out.println(Arrays.toString(rep));
        }
    }

    private static int countEmptyCells(String[][] representation) {
        int counter = 0;
        for (String[] row : representation) {
            for (String string : row) {
                if (string.equals(" ")) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static int countTakenElementsByCell(String[][] representation) {
        int counter2 = 0;
        for (String[] row : representation) {
            for (String string : row) {
                if (!string.equals(" ")) {
                    counter2++;
                }
            }
        }
        return counter2;
    }

    private static void printInfoToConsole(int counter, int counter2) {
        System.out.println("ALL EMPTY CELLS: " + counter);
        System.out.println("TAKEN CELLS: " + counter2);
        System.out.println("REMAINING EMPTY: " + (counter - counter2));
        System.out.println("ELEMENTS: 130");
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
                ""
        );

        MapElementConfiguration watersCfg = new MapElementConfiguration(
                waterSymbol,
                "waters",
                List.of(new ElementToSize(10, 1)),
                0,
                ""
        );

        List<MapElementConfiguration> elementsCfg = List.of(mountainsCfg, pitsCfg, mineralsCfg, watersCfg);
        return new MapConfiguration(1000, 0.5, elementsCfg);
    }
}
