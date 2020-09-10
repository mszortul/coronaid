package com.coronaid.coronaid;

public class NewsData {
    String date, id, img_name, out_link, source, summary, text, visible_link;

    public NewsData() {}

    public NewsData(String date, String id, String img_name, String out_link, String source, String summary, String text, String visible_link) {
        this.date = date;
        this.id = id;
        this.img_name = img_name;
        this.out_link = out_link;
        this.source = source;
        this.summary = summary;
        this.text = text;
        this.visible_link = visible_link;
    }

    public String getText() {
        return text;
    }

    public String getSummary() {
        return summary;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getImg_name() {
        return img_name;
    }

    public String getOut_link() {
        return out_link;
    }

    public String getSource() {
        return source;
    }

    public String getVisible_link() {
        return visible_link;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public void setOut_link(String out_link) {
        this.out_link = out_link;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setVisible_link(String visible_link) {
        this.visible_link = visible_link;
    }
}
