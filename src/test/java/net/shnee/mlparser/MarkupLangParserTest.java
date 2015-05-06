package net.shnee.mlparser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import net.shnee.mlparser.logger.MarkupLangParserLogger;
import org.apache.commons.io.FileUtils;
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
 * Parameterized unit test for for the MarkupLangParser interface.
 */
@RunWith(Parameterized.class)
public class MarkupLangParserTest {

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        Object[][] data = new Object[][] { {new ShneeParser()} };
        return Arrays.asList(data);
    }

    private MarkupLangParser parser = null;
    private String source = null;
    private final String htmlPath = "/test.wsdl";
    private final String googleURL = "http://www.google.com";
    private final String yahooURL = "http://www.yahoo.com";
    private final String noSrcURL = "http://hopefullythisisnotarealsite.com";
    private final String malformedURL = "malformed_url";
    private final Logger logger = MarkupLangParserLogger.MLPARSER_TESTS;

    public MarkupLangParserTest(MarkupLangParser parser) throws Exception {
        this.parser = parser;
        URL url = this.getClass().getResource("/test.html");
        File file = new File(url.getFile());

        if(file.exists()) {
            this.source = FileUtils.readFileToString(file, null);
        } else {
            throw new Exception(htmlPath + " does not exists.");
        }
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
     * Test of getSource method, of class MarkupLangParser.
     */
    @Test
    public void testSourceAccessors() throws IOException,
                                             MalformedURLException,
                                             ParseException {

        this.logger.debug("MarkupLangParserTest.testSourceAccessors - " +
                          "getSource, setSource");

        String origURL = this.parser.getURL();
        this.parser.setSource(this.source);
        assertEquals(this.source, parser.getSource());
        assertNotEquals(origURL, parser.getURL());
        assertNotEquals("", parser.getURL());
        assertTrue(parser.downloaded());
        assertFalse(parser.parsed());

        this.parser.parse();

        try {
            parser.setSource(ParserUtils.downloadURLSource(this.googleURL));
            assertNotEquals(this.source, parser.getSource());
            assertNotEquals("", parser.getURL());
            assertTrue(parser.downloaded());
            assertFalse(parser.parsed());
        } catch (IOException ex) {
            this.logger.warn("Unable to download source from " +
                             this.googleURL + " Is there an internet " +
                             "connection? ex: " + ex.getMessage());
            fail("Tests failed becuase the test was unable to download source" +
                 " from " + this.googleURL);
        }
    }

    /**
     * Test of downloadSource method, of class MarkupLangParser.
     */
    @Test
    public void testDownloadSource() throws Exception {

        this.logger.debug("MarkupLangParserTest.testDownloadSource - " +
                          "downloadSource.");

        this.parser.setURL(this.yahooURL);
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        String yahooSrc = ParserUtils.downloadURLSource(this.yahooURL);
        assertEquals(yahooSrc, this.parser.downloadSource());
        assertEquals(yahooSrc, this.parser.getSource());

        /* If we download the source again, make sure that the source object has
         * changed. This is not the best way to test this. We are assuming that
         * if the source objects are different before and after we download,
         * then that means we retrieved the source from the URL again. */
        String oldSrc = this.parser.getSource();
        this.parser.downloadSource();
        String newSrc = this.parser.getSource();
        assertFalse(oldSrc == newSrc);

        // Change the URL to a different source.
        this.parser.setURL(this.googleURL);
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        newSrc = this.parser.downloadSource();
        assertEquals(newSrc, this.parser.getSource());
        assertNotEquals(yahooSrc, newSrc);

        // Change the URL to a valid URL with no server listening.
        try {
            this.parser.setURL(this.noSrcURL);
            this.parser.downloadSource();

            // If we have gotten this far there was an error.
            fail();
        } catch(IOException ex) {
            this.logger.trace("Caught expected IOException.");
        }
    }

    @Test
    public void testURLAccessors() throws MalformedURLException {

        this.logger.debug("MarkupLangParser.testURLAccessors - getURL, setURL");

        /* Set the URL and make sure you get the same thing back when calling
         * get. */
        this.parser.setURL(this.googleURL);
        assertEquals(this.googleURL, this.parser.getURL());

        /* Manually set the source. This should force the URL to change. */
        this.parser.setSource(this.source);
        assertNotEquals(this.googleURL, this.parser.getURL());
        assertTrue(this.parser.downloaded());

        /* Set the URL to a something new. */
        this.parser.setURL(this.yahooURL);
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        assertEquals(this.yahooURL, parser.getURL());

        try {
            this.parser.setURL(this.malformedURL);

            // If we got here then the test failed.
            fail("Expected a MalformedURLException but it was never thrown.");
        } catch(MalformedURLException ex) {
            this.logger.trace("Caught expected MalformedURLException.");
        }

        // Check that the state of the MarkupLangParser has not changed.
        assertEquals(this.yahooURL, parser.getURL());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());

        // Will it throw an error two times in a row?
        try {
            parser.setURL(this.malformedURL);

            // If we got here then the test failed.
            fail("Expected a MalformedURLException but it was never thrown.");
        } catch(MalformedURLException ex) {
            this.logger.trace("Caught expected MalformedURLException.");
        }
        
        // Set to the URL to the same value.
        this.parser.setURL(this.yahooURL);
        assertEquals(this.yahooURL, parser.getURL());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());

        // Set it again after the error for good measure.
        parser.setURL(this.googleURL);
        assertEquals(this.googleURL, parser.getURL());
    }

    /**
     * Test of parse & parsed methods, of class MarkupLangParser.
     */
    @Test
    public void testParse() throws MalformedURLException,
                                   ParseException,
                                   IOException {

        this.logger.debug("MarkupLangParser.testParse - parse, parsed");

        /* Set the URL to something different. If we set to the same URL then
         * the internal state will not be reset. */
        String url;
        if(this.parser.getURL().equals(this.googleURL))
        {
            url = this.yahooURL;
        } else {
            url = this.googleURL;
        }
        this.parser.setURL(url);
        
        // Check state of MarkupLangParser.
        assertEquals(url, this.parser.getURL());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        assertNull(this.parser.getSource());
        assertNull(this.parser.getRootNode());
        
        // Call parse without downloading source.
        try {
            this.parser.parse();
            
            // The test has failed if we've gotten to this point.
            fail("Expected a IOException, but one was not thrown.");
        } catch(IOException ex) {
            this.logger.trace("Caught expected IOException.");
        }
        
        assertEquals(url, this.parser.getURL());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        assertNull(this.parser.getSource());
        assertNull(this.parser.getRootNode());
        
        /* Download the source before calling parse and then check the internal
         * state of the MarkupLangParser. */
        this.parser.downloadSource();
        
        assertEquals(url, this.parser.getURL());
        assertTrue(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        assertNotNull(this.parser.getSource());
        assertNull(this.parser.getRootNode());
        
        this.parser.parse();
        
        assertEquals(url, this.parser.getURL());
        assertTrue(this.parser.downloaded());
        assertTrue(this.parser.parsed());
        assertNotNull(this.parser.getSource());
        assertNotNull(this.parser.getRootNode());

    }

    /**
     * Test of getRootNode method, of class MarkupLangParser.
     */
    @Test
    public void testGetRootNode() throws MalformedURLException,
                                         IOException,
                                         ParseException {
        
        this.logger.debug("MarkupLangParser.testGetRootNode - getRootNode");

        /* Set the URL to something different. If we set to the same URL then
         * the internal state will not be reset. */
        String url;
        if(this.parser.getURL().equals(this.googleURL))
        {
            url = this.yahooURL;
        } else {
            url = this.googleURL;
        }
        this.parser.setURL(url);
        
        // Check state of MarkupLangParser.
        assertEquals(url, this.parser.getURL());
        assertFalse(this.parser.downloaded());
        assertFalse(this.parser.parsed());
        assertNull(this.parser.getSource());
        assertNull(this.parser.getRootNode());
        
        this.parser.downloadSource();
        assertEquals(this.parser.parse(), this.parser.getRootNode());
        
        // Check state of MarkupLangParser.
        assertEquals(url, this.parser.getURL());
        assertTrue(this.parser.downloaded());
        assertTrue(this.parser.parsed());
        assertNotNull(this.parser.getSource());
        
    }

}
