package net.shnee.mlparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * A XML/HTML parser that implements MarkupLangParser and inherits from
 * AbstractMarkupLangParser.
 */
public class ShneeParser extends AbstractMarkupLangParser {

    /** Document that is generated after parsing
     *  {@link AbstractMarkupLangParser#source}. */
    private Document doc = null;

    /**
     * Empty constructor for ShneeParser. getSource() and getUrl() will be null.
     */
    public ShneeParser() {}

    /**
     * Constructor for ShneeParser. Sets getURL to url and getSource() to null.
     * @param url
     */
    public ShneeParser(String url) {
        super(url);
    }

    /**
     * @see AbstractMarkupLangParser#parse().
     */
    @Override
    public Node parse() throws MalformedURLException,
                               IOException,
                               ParseException {
        // if url is null throw exception
        if(this.url == null) {
            this.logger.error("Call to parse() before target URL has been " +
                              "set.");
            throw new MalformedURLException("Call to parse() before the " +
                                            "target URL has been set.");
        }

        // Make sure the source has been downloaded.
        else if(!this.downloaded()) {
            throw new IOException("Unable to parse source because it has not " +
                                  "been downloaded.");
        }

        /* Check if the source has already been parsed. */
        else if(!this.parsed()) {
            this.doc = Jsoup.parse(this.source);
            this.rootNode = new ShneeNode(this.doc.body());
        }

        return this.rootNode;
    }

}
