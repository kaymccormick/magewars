package us.heptet.magewars.test;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/* Created by kay on 6/27/2014. */
/**
 * Annotation for marking a relationship between a test method an an external test case.
 */
@Target({METHOD})
public @interface ExternalTestCase {
    /**
     * The ID of the test case.
     * @return String value
     */
    String value();
}
