package com.duanjiefei.github.liveplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.duanjiefei.github.liveplayer.application.AppManager;

public class Album implements Parcelable{

    private String albumId;//专辑id
    private int videoTotal;//集数
    private String title;//专辑名称
    private String subTitle;//专辑子标题
    private String director;//导演
    private String mainActor;//主演
    private String verImgUrl;//专辑竖图
    private String horImgUrl;//专辑横图
    private String albumDesc;//专辑描述
    private Site site;//网站
    private String tip;//提示
    private boolean isCompleted;//专辑是否更新完
    private String letvStyle;//乐视特殊字段


    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getVideoTotal() {
        return videoTotal;
    }

    public void setVideoTotal(int videoTotal) {
        this.videoTotal = videoTotal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getVerImgUrl() {
        return verImgUrl;
    }

    public void setVerImgUrl(String verImgUrl) {
        this.verImgUrl = verImgUrl;
    }

    public String getHorImgUrl() {
        return horImgUrl;
    }

    public void setHorImgUrl(String horImgUrl) {
        this.horImgUrl = horImgUrl;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLetvStyle() {
        return letvStyle;
    }

    public void setLetvStyle(String letvStyle) {
        this.letvStyle = letvStyle;
    }


    public static final Parcelable.Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        @Override
        public Album[] newArray(int i) {
            return new Album[i];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(albumId);
        parcel.writeInt(videoTotal);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeString(director);
        parcel.writeString(mainActor);
        parcel.writeString(verImgUrl);
        parcel.writeString(horImgUrl);
        parcel.writeString(albumDesc);
        parcel.writeString(tip);
        parcel.writeInt(site.getSiteID());
        parcel.writeByte((byte) (isCompleted() ? 1: 0));
        parcel.writeString(letvStyle);
    }

    public Album(int siteID){
        site = new Site(siteID);
    }

    private Album(Parcel in) {
        this.albumId = in.readString();
        this.videoTotal = in.readInt();
        this.title = in.readString();
        this.subTitle = in.readString();
        this.director = in.readString();
        this.mainActor = in.readString();
        this.verImgUrl = in.readString();
        this.horImgUrl = in.readString();
        this.albumDesc = in.readString();
        this.tip = in.readString();
        this.site = new Site(in.readInt());
        this.isCompleted = in.readByte() != 0;
        this.letvStyle = in.readString();
    }



    @Override
    public String toString() {
        return "Album{" +
                "albumId='" + albumId + '\'' +
                ", videoTotal=" + videoTotal +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", director='" + director + '\'' +
                ", mainActor='" + mainActor + '\'' +
                ", verImgUrl='" + verImgUrl + '\'' +
                ", horImgUrl='" + horImgUrl + '\'' +
                ", albumDesc='" + albumDesc + '\'' +
                ", site=" + site +
                ", tip='" + tip + '\'' +
                ", isCompleted=" + isCompleted +
                ", letvStyle='" + letvStyle + '\'' +
                '}';
    }

    public String toJsonString(){
        return AppManager.getGson().toJson(this);
    }

    public Album fromGson(String JsonString){
        return AppManager.getGson().fromJson(JsonString,Album.class);
    }
}
