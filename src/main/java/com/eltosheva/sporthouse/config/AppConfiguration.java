package com.eltosheva.sporthouse.config;

import com.eltosheva.sporthouse.utils.ValidationUtil;
import com.eltosheva.sporthouse.utils.ValidationUtilImpl;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime
                        .parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate
                        .parse(json.getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> LocalTime
                        .parse(json.getAsString(), DateTimeFormatter.ofPattern("HH:mm")))
                .create();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidationUtil validatorUtil() {
        return new ValidationUtilImpl(validator());
    }
}
