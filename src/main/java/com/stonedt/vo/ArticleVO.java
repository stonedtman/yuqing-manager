package com.stonedt.vo;

import java.util.Objects;

/**
 * @author 文轩
 * 文章VO
 */
public class ArticleVO {

    private String id;

    private String title;


    private String author;


    private String spiderTime;

    private String publishTime;

    public ArticleVO() {
    }

    public ArticleVO(String id, String title, String author, String spiderTime, String publishTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.spiderTime = spiderTime;
        this.publishTime = publishTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSpiderTime() {
        return spiderTime;
    }

    public void setSpiderTime(String spiderTime) {
        this.spiderTime = spiderTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleVO articleVO = (ArticleVO) o;
        return Objects.equals(id, articleVO.id) && Objects.equals(title, articleVO.title) && Objects.equals(author, articleVO.author) && Objects.equals(spiderTime, articleVO.spiderTime) && Objects.equals(publishTime, articleVO.publishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, spiderTime, publishTime);
    }

    @Override
    public String toString() {
        return "ArticleVO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", spiderTime='" + spiderTime + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
