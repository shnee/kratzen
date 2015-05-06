package net.shnee.mlparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * Interface for a XML/HTML parser.
 */
public interface MarkupLangParser {

    /**
     * Get the source of the parser.
     * @return Returns the source of the parser.
     */
    public String getSource();

    /**
     * Set the source for the MarkupLangParser. This will set downloaded to
     * true, URL will be set to non-empty string, and parsed to false.
     * @param source The new source for the MarkupLangParser.
     */
    public void setSource(String source);

    /**
     * Retrieves the source at getURL(). Sets the source of getSource(). Throws
     * IOException if there was a an error grabbing the source at getURL().
     * downloadSource will execute every time it is called, even if the URL has
     * not changed.
     * @return Returns the source at getURL().
     * @throws java.io.IOException Throws IOException if there was a an error
     *                             grabbing the source at getURL().
     * @throws java.net.MalformedURLException Throws a MalformedURLException if
     *                                        the URL is malformed.
     */
    public String downloadSource() throws IOException, MalformedURLException;

    /**
     * Get the URL of the parser.
     * @return Returns the URL of the parser.
     */
    public String getURL();

    /**
     * Set the URL of the parser. If the URL has changed then the root Node and
     * the source is set to NULL. Throws MalformedURLException if the URL is in
     * a invalid format. The URL will retain it's old value if there was an
     * error when trying to set a new one.
     * @param url The new URL for the MarkupLangParser.
     * @throws MalformedURLException Throws MalformedURLException if the URL is
     *                               in a invalid format.
     */
    public void setURL(String url) throws MalformedURLException;

    /**
     * Parse the source and return the root Node. Throws an
     * MalformedURLException if the URL has not yet been set. Throws IOException
     * if the source has not been downloaded. Throws a ParseException if there
     * was an error parsing the source.
     * @return Returns the root Node.
     * @throws MalformedURLException Throws MalformedURLException if the URL has
     *                               not yet been set.
     * @throws IOException Throws IOException if the source has not been
     *                     downloaded.
     * @throws ParseException Throws ParseException is there was an error
     *                        parsing the source.
     */
    public Node parse() throws MalformedURLException,
                               IOException,
                               ParseException;

    /**
     * Returns true if the source has been parsed, false otherwise.
     * @return true if the source has been parsed, false otherwise.
     */
    public boolean parsed();

    /**
     * Get the root Node of the source.
     * @return Returns the root Node.
     */
    public Node getRootNode();

    /**
     * Returns true if the source has been downloaded, false if not.
     * @return Returns true if the source has been downloaded, false if not.
     */
    public Boolean downloaded();

}
