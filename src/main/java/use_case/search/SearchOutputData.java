package use_case.search;

import entity.AbstractMedia;

/**
 * Contains the output data for the search results.
 */
public class SearchOutputData {
    private final AbstractMedia media;

    public SearchOutputData(AbstractMedia media) {
        this.media = media;
    }

    public AbstractMedia getMedia() {
        return media;
    }
}
