/*
 * 文件名：ResultPager.java
 * 版权：Copyright by bonc
 * 描述：
 * 修改人：root
 * 修改时间：2016年9月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


/**
 * queryHelper
 * 
 * @author zhangyunzhen
 * @version 2017年3月10日
 * @see ResultPager
 * @since
 */
public class ResultPager {
    private static Integer pageSize = 10;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        ResultPager.pageSize = pageSize;
    }

    /**
     * 
     * Description:
      *  构造PageRequest对象,
     * springData包中的 PageRequest类已经实现了Pageable接口
     * @param pageNumber Integer
     * @param pagzSize Integer
     * @return 
     * @see
     */
    public static PageRequest buildPageRequest(Integer pageNumber, Integer size) {
        if (null == pageNumber || 0 == pageNumber) {
            pageNumber = 1;
        }
        if (null == size) {
            size = pageSize;
        }
        return new PageRequest(pageNumber - 1, size, null);
    }

    /**
    * Description:
    *  构造PageRequest对象,
    * springData包中的 PageRequest类已经实现了Pageable接口
    * @param pageNumber Integer
    * @param pagzSize Integer
    * @param sort Sort
    * @return 
    * @see
    */
    public static PageRequest buildPageRequest(Integer pageNumber, Integer size, Sort sort) {
        if (null == pageNumber || 0 == pageNumber) {
            pageNumber = 1;
        }
        if (null == size || 0 == size) {
            size = pageSize;
        }
        return new PageRequest(pageNumber - 1, size, sort);
    }

    /**
     * 
     * Description:
     * 构造PageRequest对象,
     * springData包中的 PageRequest类已经实现了Pageable接口
     * @param pageNumber String
     * @param pagzSize String
     * @return 
     * @see
     */
    public static PageRequest buildPageRequest(String pageNumber, String size) {
        if (StringUtils.isBlank(pageNumber)) {
            pageNumber = "1";
        }
        if (StringUtils.isBlank(size)) {
            size = pageSize.toString();
        }
        return buildPageRequest(Integer.valueOf(pageNumber), Integer.valueOf(size));
    }
}
