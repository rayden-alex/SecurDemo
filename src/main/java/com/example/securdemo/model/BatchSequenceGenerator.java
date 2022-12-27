package com.example.securdemo.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PACKAGE, TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@GenericGenerator(
        name = "",
        strategy = "com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator",
        parameters = {
                @Parameter(name = SEQUENCE_PARAM, value = ""),
                @Parameter(name = FETCH_SIZE_PARAM, value = "20")
        })
public @interface BatchSequenceGenerator {
        /**
         * unique generator name.
         */
        @AliasFor(annotation = GenericGenerator.class, attribute = "name")
        String name() default "";

        /**
         * Optional generator parameters.
         */
        @AliasFor(annotation = GenericGenerator.class, attribute = "parameters")
        Parameter[] parameters() default {
                @Parameter(name = SEQUENCE_PARAM, value = ""),
                @Parameter(name = FETCH_SIZE_PARAM, value = "20")
        };
}
