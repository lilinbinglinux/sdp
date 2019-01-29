package com.sdp.pageModel.entity;

import com.sdp.common.entity.BaseInfo;

/**
 * @description:
 * 页面模板实体类
 * @author: zhoutao
 * @version: 11:26 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageModel extends BaseInfo{
	
    private String pageId;

    /**
     * 页面分类
     */
    private String pageTypeId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面内容
     */
    private String pageText;

    /**
     * 纯净的页面内容
     */
    private String pagePureText;

    /**
     * 页面样式内容css
     */
    private String pageStyle;

    /**
     * 页面js内容
     */
    private String pageJs;

    /**
     * 序号
     */
    private int pageSortId;

    /**
     * 登录id
     */
    private String loginId;
    
    /**
     * 租户id
     */
    private String tenantId;
    
    /**
     * 项目id
     */
    private String projectId;

    
    public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageTypeId() {
        return pageTypeId;
    }

    public void setPageTypeId(String pageTypeId) {
        this.pageTypeId = pageTypeId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPagePureText() {
        return pagePureText;
    }

    public void setPagePureText(String pagePureText) {
        this.pagePureText = pagePureText;
    }

    public String getPageText() {
        return pageText;
    }

    public void setPageText(String pageText) {
        this.pageText = pageText;
    }

    public String getPageStyle() {
        return pageStyle;
    }

    public void setPageStyle(String pageStyle) {
        this.pageStyle = pageStyle;
    }

    public String getPageJs() {
        return pageJs;
    }

    public void setPageJs(String pageJs) {
        this.pageJs = pageJs;
    }

    public int getPageSortId() {
        return pageSortId;
    }

    public void setPageSortId(int pageSortId) {
        this.pageSortId = pageSortId;
    }

	public PageModel(String pageId, String pageTypeId, String pageName, String pageText, String pagePureText, String pageStyle,
			String pageJs, int pageSortId) {
		super();
		this.pageId = pageId;
		this.pageTypeId = pageTypeId;
		this.pageName = pageName;
		this.pageText = pageText;
        this.pagePureText = pagePureText;
		this.pageStyle = pageStyle;
		this.pageJs = pageJs;
		this.pageSortId = pageSortId;
	}

	public PageModel() {
		super();
	}
    
    
}
