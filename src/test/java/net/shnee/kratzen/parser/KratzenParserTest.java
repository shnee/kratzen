package net.shnee.kratzen.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import net.shnee.kratzen.logger.KratzenLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;

/**
 * Unit test for KratzenParser. This test only covers what was not tested in the
 * unit test of the parent class and interface.
 */
public class KratzenParserTest {

    private final Logger logger = KratzenLogger.MLPARSER_TESTS;
    private final String url = "http://www.google.com";

    public KratzenParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test the KratzenParser(String url) constructor.
     */
    @Test
    public void testURLConstructor() {

        this.logger.debug("ShneeParser.testURLConstructor - Test the " +
                          "ShneeParser(String url) constructor.");

        KratzenParser urlParser = new KratzenParser(this.url);

        // Check state of urlParser.
        assertEquals(this.url, urlParser.getURL());
        assertNull(urlParser.getSource());
        assertNull(urlParser.getRootNode());
        assertFalse(urlParser.downloaded());
        assertFalse(urlParser.parsed());

        KratzenParser nullParser = new KratzenParser();

        // Check state of nullParser.
        assertNull(nullParser.getURL());
        assertNull(nullParser.getSource());
        assertNull(nullParser.getRootNode());
        assertFalse(nullParser.downloaded());
        assertFalse(nullParser.parsed());
    }

    /**
     * Test of parse method when the URL has not been set.
     */
    @Test
    public void testParseWithNullURL() throws IOException, ParseException {

        this.logger.debug("ShneeParser.testParse - Test of parse method with " +
                          "null for the URL.");

        KratzenParser parser = new KratzenParser();

        try {
            parser.parse();

            // If we got here then the test has failed.
            fail("Expected a MalformedURLException in testParseWithNullURL " +
                 "but none was thrown.");
        } catch(MalformedURLException ex) {
            this.logger.trace("Caught expected MalformedURLException in " +
                              "testParseWithNullURL");
        }

    }

}
