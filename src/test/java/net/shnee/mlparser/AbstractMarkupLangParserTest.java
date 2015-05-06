package net.shnee.mlparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import net.shnee.mlparser.logger.MarkupLangParserLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;

/**
 * Unit test for AbstractMarkLangParser. This unit test will cover everything not
 * covered in MarkupLangParserTest.
 */
@RunWith(Parameterized.class)
public class AbstractMarkupLangParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        Object[][] data = new Object[][] { {new ShneeParser()} };
        return Arrays.asList(data);
    }

    private AbstractMarkupLangParser parser = null;
    private final Logger logger = MarkupLangParserLogger.MLPARSER_TESTS;
    private final String url = "http://www.yahoo.com";

    public AbstractMarkupLangParserTest(AbstractMarkupLangParser parser) {
        this.parser = parser;
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
     * Test the AbstractMarkupLangParser(String url) constructor.
     */
    @Test
    public void testURLConstructor() {

        this.logger.debug("AbstractMarkupLangParserTest.testURLConstructor" +
                          " - Test the AbstractMarkupLangParser(String url) " +
                          "constructor.");

        AbstractMarkupLangParserImpl urlParser =
                new AbstractMarkupLangParserImpl(this.url);

        // Check state of urlParser.
        assertEquals(this.url, urlParser.getURL());
        assertNull(urlParser.getSource());
        assertNull(urlParser.getRootNode());
        assertFalse(urlParser.downloaded());
        assertFalse(urlParser.parsed());

        AbstractMarkupLangParserImpl nullParser =
                new AbstractMarkupLangParserImpl();

        // Check state of nullParser.
        assertNull(nullParser.getURL());
        assertNull(nullParser.getSource());
        assertNull(nullParser.getRootNode());
        assertFalse(nullParser.downloaded());
        assertFalse(nullParser.parsed());

    }

    /**
     * Test of clear method, of class AbstractMarkupLangParser.
     */
    @Test
    public void testClear() throws MalformedURLException,
                                   IOException,
                                   ParseException {

        this.logger.debug("AbstractMarkupLangParserTest.testClear - clear");

        this.parser.clear();

        // Check internal state of the AbstractMarkupLangParser.
        assertNull(this.parser.getURL());
        assertNull(this.parser.getSource());
        assertNull(this.parser.getRootNode());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());

        // Set the AbstractMarkupLangParser to a different state.
        this.parser.setURL(this.url);
        this.parser.downloadSource();
        this.parser.parse();

        // Verify that the AbstractMarkupLangParser is ina different state.
        assertNotNull(this.parser.getURL());
        assertNotNull(this.parser.getSource());
        assertNotNull(this.parser.getRootNode());
        assertTrue(this.parser.downloaded());
        assertTrue(this.parser.parsed());

        /* Verify the internal state of the AbstractMarkupLangParser after
         * calling clear. */
        this.parser.clear();
        assertNull(this.parser.getURL());
        assertNull(this.parser.getSource());
        assertNull(this.parser.getRootNode());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());

    }

    /**
     * Test of AbstractMarkupLangParser.downloadSource method when the URL has
     * not been set.
     */
    @Test
    public void testDownloadSourceWithNullURL() throws IOException {

        this.logger.debug("AbstractMarkupLangParserTest." +
                          "testDownloadSourceWithNullURL - downloadSource " +
                          "when the URL is null.");

        this.parser.clear();

        try {
            this.parser.downloadSource();

            // If we got here then the test has failed.
            fail("Expected a MalformedURLException in " +
                 "testDownloadSourceWithNullURL but none was thrown.");
        } catch(MalformedURLException ex) {
            this.logger.trace("Caught expected MalformedURLException in " +
                              "testDownloadSourceWithNullURL");
        }

    }

    /**
     * Test the URL value when the URL has been manually set.
     */
    @Test
    public void testManualSourceURL() throws MalformedURLException {

        this.logger.debug("AbstractMarkupLangParserTest." +
                          "testManualSourceURL - Test the URL value when the " +
                          "URL has been manually set.");

        // Start fresh and check the initial state.
        this.parser.clear();
        assertNull(this.parser.getURL());

        // Set the source manually and check the URL.
        this.parser.setSource("<html><body><h1>YAY!</h1></body><html>");
        assertEquals(AbstractMarkupLangParser.manualSourceURL,
                     this.parser.getURL());

        /* Set the URL to something different and check that we get something
         * different back other than AbstractMarkupLangParser.manualSourceURL.
         */
        this.parser.setURL(this.url);
        assertNotEquals(AbstractMarkupLangParser.manualSourceURL,
                        this.parser.getURL());

    }

    class AbstractMarkupLangParserImpl extends AbstractMarkupLangParser {

        public AbstractMarkupLangParserImpl() { super(); }

        public AbstractMarkupLangParserImpl(String url) { super(url); }

        @Override
        public Node parse() throws MalformedURLException,
                                   IOException,
                                   ParseException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}
