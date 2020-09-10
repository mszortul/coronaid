package com.coronaid.coronaid;


import com.google.firebase.storage.StorageReference;

public class NewsPost {

    String summary, id, vlink, date, olink, source, text, imgName;
    StorageReference imgRef;

    public NewsPost(String date, String id, String vlink, String summary, StorageReference imgRef, String olink, String source, String text, String imgName) {
        this.date = date;
        this.id = id;
        this.imgRef = imgRef;
        this.vlink = vlink;
        this.summary = summary;
        this.olink = olink;
        this.source = source;
        this.text = text;
        this.imgName = imgName;
    }

    public String getDate() { return date; }
    public String getId() { return id; }
    public String getVlink() { return vlink; }
    public String getSummary() { return summary; }
    public StorageReference getImgRef() { return imgRef; }
    public String getOlink() { return olink; }
    public String getSource() { return source; }
    public String getText() { return text; }
    public String getImgName() { return imgName; }

    public void setDate(String time) { this.date = date; }
    public void setId(String id) { this.id = id; }
    public void setVlink(String vlink) { this.vlink = vlink; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setImgRef(StorageReference imgRef) { this.imgRef = imgRef; }
    public void setOlink(String olink) { this.olink = olink; }
    public void setSource(String source) { this.source = source; }
    public void setText(String text) { this.text = text; }
    public void setImgName(String imgName) { this.imgName = imgName; }
}
