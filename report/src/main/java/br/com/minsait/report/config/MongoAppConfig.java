package br.com.minsait.report.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Configuration class for MongoDB.
 * @author Leonardo Bernardino
 */
@Configuration
public class MongoAppConfig extends AbstractMongoClientConfiguration {

    private List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Value("${spring.data.mongodb.database}")
    private String db;

    /**
     * Returns the default MongoDB converter.
     *
     * @param factory the MongoDB factory
     * @return the default MongoDB converter
     */
    private static MongoConverter getDefaultMongoConverter(MongoDbFactory factory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MongoCustomConversions conversions = new MongoCustomConversions(Collections.emptyList());

        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        mappingContext.afterPropertiesSet();

        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(conversions);
        converter.afterPropertiesSet();

        return converter;
    }

    /**
     * Bean to configure the MongoDB client.
     *
     * @return the configured MongoDB client
     */
    @Bean
    public com.mongodb.client.MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    /**
     * Returns the name of the MongoDB database.
     *
     * @return the name of the database
     */
    @Override
    protected String getDatabaseName() {
        return db;
    }
}
