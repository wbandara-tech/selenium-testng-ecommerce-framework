package com.wbandara.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * AnnotationTransformer - Automatically applies RetryAnalyzer to all test methods.
 */
public class AnnotationTransformer implements IAnnotationTransformer {
    private static final Logger logger = LogManager.getLogger(AnnotationTransformer.class);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Apply RetryAnalyzer to all tests that don't have one specified
        if (annotation.getRetryAnalyzerClass() == null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
            logger.debug("Applied RetryAnalyzer to test method: {}",
                    testMethod != null ? testMethod.getName() : "unknown");
        }
    }
}
