package com.quakd.web.data;

public enum SocialPostType {

    FACEBOOK("F"),
    TWITTER("T"),
    NONE("N"),
    ALL("A");
    
    /**
     * @param text
     */
    private SocialPostType(final String text) {
        this.text = text;
    }

    private final String text;

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
