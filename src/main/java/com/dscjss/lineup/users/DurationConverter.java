package com.dscjss.lineup.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

    private final Logger logger = LoggerFactory.getLogger(DurationConverter.class);

    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        if(attribute != null) {
            return attribute.toMillis();
        }
        return 0L;
    }

    @Override
    public Duration convertToEntityAttribute(Long duration) {
        if(duration != null) {
            return Duration.of(duration, ChronoUnit.MILLIS);
        }
        return null;
    }
}