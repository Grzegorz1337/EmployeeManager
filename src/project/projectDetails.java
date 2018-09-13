package project;

public class projectDetails {
    private String name;
    private String manager;
    private String grafik;
    private String dbmaster;
    private String desktop;
    private String mobile;
    private String web;
    private String client_service;
    private String peon;
    private String website;
    private String scrum;

    public projectDetails(String name, String manager, String grafik, String dbmaster, String desktop, String mobile, String web, String client_service, String peon, String website, String scrum) {
        this.name = name;
        this.manager = manager;
        this.grafik = grafik;
        this.dbmaster = dbmaster;
        this.desktop = desktop;
        this.mobile = mobile;
        this.web = web;
        this.client_service = client_service;
        this.peon = peon;
        this.website = website;
        this.scrum = scrum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getGrafik() {
        return grafik;
    }

    public void setGrafik(String grafik) {
        this.grafik = grafik;
    }

    public String getDbmaster() {
        return dbmaster;
    }

    public void setDbmaster(String dbmaster) {
        this.dbmaster = dbmaster;
    }

    public String getDesktop() {
        return desktop;
    }

    public void setDesktop(String desktop) {
        this.desktop = desktop;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getClient_service() {
        return client_service;
    }

    public void setClient_service(String client_service) {
        this.client_service = client_service;
    }

    public String getPeon() {
        return peon;
    }

    public void setPeon(String peon) {
        this.peon = peon;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getScrum() {
        return scrum;
    }

    public void setScrum(String scrum) {
        this.scrum = scrum;
    }

}