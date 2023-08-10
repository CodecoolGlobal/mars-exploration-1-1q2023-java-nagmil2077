package com.codecool.marsexploration.output.service;

import com.codecool.marsexploration.mapelements.model.Map;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MapFileWriterImplTest {

    @Test
    void testWriteMapFile() {
        MapFileWriter writer = new MapFileWriterImpl();
        Map testMap = new Map(new String[][]{
                {"A", "B", "C"},
                {"D", "E", "F"}
        });
        String testDir = "src/test/testResources/";
        File expectedOutputFile = new File(testDir + "generated.map");

        writer.writeMapFile(testMap, testDir);

        assertTrue(expectedOutputFile.exists());
    }
}
