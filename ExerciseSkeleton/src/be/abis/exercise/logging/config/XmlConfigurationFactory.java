package be.abis.exercise.logging.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.io.IOException;

@Plugin(name="XmlConfigurationFactory", category=ConfigurationFactory.CATEGORY)
@Order(50)
public class XmlConfigurationFactory extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.WARN);
        AppenderComponentBuilder appenderBuilder = builder.newAppender("file", "File").addAttribute("fileName", "mylog.log");
        appenderBuilder.add(builder.newLayout("XmlLayout"));
        builder.add(appenderBuilder);
        builder.add(builder.newLogger("be.abis.exercise", Level.DEBUG).
                add(builder.newAppenderRef("file")).addAttribute("additivity", true));
        builder.add(builder.newRootLogger(Level.WARN).
                add(builder.newAppenderRef("file")));
        try {
            builder.writeXmlConfiguration(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[0];
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return null;
    }
}
