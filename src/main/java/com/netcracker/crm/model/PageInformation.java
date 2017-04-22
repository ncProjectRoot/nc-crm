package com.netcracker.crm.model;

/**
 * @author Karpunets
 * @since 21.04.2017
 */
public class PageInformation {

    private final String title;
    private final String icon;
    private final String href;

    public PageInformation(String title, String icon, String href) {
        this.title = title;
        this.icon = icon;
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }
}
