package com.codecool.marsexploration.output.service;

import com.codecool.marsexploration.mapelements.model.Map;

import java.io.FileWriter;
import java.io.IOException;

public class MapFileWriterImpl implements MapFileWriter {

    @Override
    public void writeMapFile(Map map, String fileDir) {
        String formattedMap = map.toString();
        String fileName = "generated.map";

        try {
            FileWriter writer = new FileWriter(fileDir + "/" + fileName);
            writer.write(formattedMap);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Map file written successfully.");
    }
}
