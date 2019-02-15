/*
 * 文件名：MaterialBean.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.weixin.bean;

/**
 *  图文消息素材bean
 * @author zhangyunzhen
 * @version 2017年7月12日
 * @see MaterialBean
 * @since
 */
public class MaterialBean {
    /**
     * 图文消息缩略图的media_id
     */
    private String thumb_media_id;

    /**
     * 图文消息的作者
     */
    private String author;

    /**
     * 图文消息的标题
     */
    private String title;

    /**
     * 在图文消息页面点击“阅读原文”后的页面   
     */
    private String content_source_url;

    /**
     * 图文消息页面的内容，支持HTML标签。
     */
    private String content;

    /**
     * 图文消息的描述，如本字段为空，则默认抓取正文前64个字
     */
    private String digest;

    /**
     * 是否显示封面，1为显示，0为不显示
     */
    private Integer show_cover_pic;

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_source_url() {
        return content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getShow_cover_pic() {
        return show_cover_pic;
    }

    public void setShow_cover_pic(Integer show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }

}
