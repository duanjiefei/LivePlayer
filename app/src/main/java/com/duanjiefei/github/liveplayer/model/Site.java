package com.duanjiefei.github.liveplayer.model;

public class Site {
    private static final int SOHU = 2;
    private static final int LETV = 1;
    public  static final int MAX_COUNT = 2;

    private int siteID =1;
    private String siteName = "";

    private static  final  int MAX_SITE = 2;


    public Site(int siteNo){
        siteID = siteNo;
        switch (siteID){
            case SOHU:
                siteName = "搜狐视频";
                break;
            case LETV:
                siteName = "乐视视频";
                break;
        }
    }
    public int getSiteID() {
        return siteID;
    }

    public String getSiteName() {
        return siteName;
    }
}
