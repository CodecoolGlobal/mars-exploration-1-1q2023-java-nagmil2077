package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.MapConfiguration;

public class MapConfigurationValidatorImpl implements MapConfigurationValidator{
    @Override
    public boolean validate(MapConfiguration mapConfig) {
        if (mapConfig.elementToSpaceRatio() <= 0.5){
            return true;
        }else {

            return false;
        }
    }
}
