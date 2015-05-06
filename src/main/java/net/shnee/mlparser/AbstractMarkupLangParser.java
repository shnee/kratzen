package net.shnee.mlparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import net.shnee.mlparser.logger.MarkupLangParserLogger;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;

/**
 * Abstract class for MarkupLangParsers. All common code goes here.
 */
public abstract class AbstractMarkupLangParser implements MarkupLangParser {

    /** The location of the source to be parsed. It's NULL if it has not been
     *  initialized. */
    protected String url = null;

    /** The source to be parsed. It's NULL if it has not been initialized. */
    protected String source = null;

    /** The root Node of the source. */
    protected Node rootNode = null;

    /**
     * The URL gets set to this string when the source has been set manually
     * instead of downloaded.
     */
    static public final String manualSourceURL = "manual";

    /** Logger for MarkupLangParser. */
    protected Logger logger = MarkupLangParserLogger.MLPARSER;

    /**
     * Empty constructor for AbstractMarkupLangParser. getSource() and getUrl()
     * will be null.
     */
    protected AbstractMarkupLangParser() {
    }

    /**
     * Constructor for AbstractMarkupLangParser. Sets getURL to url and
     * getSource() to null.
     * @param url URL for the MarkupLangParser.
     */
    protected AbstractMarkupLangParser(String url) {
        this.url = url;
    }

    /**
     * package-private method to clear/reset the AbstractMarkupLangParser.
     */
    void clear() {
        this.rootNode = null;
        this.source = null;
        this.url = null;
    }

    /**
     * @see MarkupLangParser#getSource().
     */
    @Override
    public String getSource() { return this.source; }

    /**
     * @see MarkupLangParser#setSource().
     */
    @Override
    public void setSource(String source) {
        this.source = source;
        this.rootNode = null;
        this.url = AbstractMarkupLangParser.manualSourceURL;
    }

    /**
     * @see MarkupLangParser#downloadSource()
     */
    @Override
    public String downloadSource() throws MalformedURLException, IOException {
        if((this.url != null) && (!this.url.isEmpty())) {
            this.source = ParserUtils.downloadURLSource(this.url);
        } else {
            throw new MalformedURLException("URL is empty.");
        }

        return this.source;
    }

    /**
     * @see MarkupLangParser#getURL().
     */
    @Override
    public String getURL() { return this.url; }

    /**
     * @see MarkupLangParser#setURL(java.lang.String).
     */
    @Override
    public void setURL(String url) throws MalformedURLException {
        UrlValidator urlValidator = new UrlValidator();
        if(urlValidator.isValid(url)) {
            // Check if the URL has changed, if so reset rootNode and source.
            if(!url.equals(this.url)) {
                this.rootNode = null;
                this.source = null;
                this.url = url;
                this.logger.debug("Set URL to {}, reset the root node and " +
                                  "source.", this.url);
            } else {
                this.logger.debug("New URL matched the old one, doing " +
                                  "nothing: ", this.url);
            }
        } else {
            this.logger.error("Can't set URL to malformed string {}", url);
            throw new MalformedURLException("Cannot set URL to " + url +
                                            ", it is an invalid format.");
        }

    }

    /**
     * @see MarkupLangParser#parse()
     */
    @Override
    public abstract Node parse() throws MalformedURLException,
                                        IOException,
                                        ParseException;

    /**
     * @see MarkupLangParser#parsed()
     */
    @Override
    public boolean parsed() { return this.rootNode != null; }

    /**
     * @see MarkupLangParser#getRootNode()
     */
    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    /**
     * @see MarkupLangParser#downloaded()
     * @return
     */
    @Override
    public Boolean downloaded() { return this.source != null; }

}
