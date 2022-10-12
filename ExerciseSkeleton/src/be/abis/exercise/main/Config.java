package be.abis.exercise.main;

import be.abis.exercise.logging.config.XmlConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

public class Config {

    public static void main(String[] args) {
        ConfigurationFactory.setConfigurationFactory(new XmlConfigurationFactory());
    }

}
