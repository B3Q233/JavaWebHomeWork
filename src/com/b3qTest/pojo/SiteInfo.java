package com.b3qTest.pojo;

/**
 * @author b3q
 * siteInfo JAVA Bean
 */
public class SiteInfo {

    public SiteInfo(){

    }

    private int id;
    private String siteName;
    private String siteDescription;
    private String siteDomain;
    private String siteKeyWords;
    private String logoImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain;
    }

    public String getSiteKeyWords() {
        return siteKeyWords;
    }

    public void setSiteKeyWords(String siteKeyWords) {
        this.siteKeyWords = siteKeyWords;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

}
