package com.sdp.common.page;
/**
 * 分页接口
 * 
 * @author llb
 * 
 */
public interface Paginable {
	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public Long getTotalCount();

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public Long getTotalPage();

	/**
	 * 每页记录数
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 当前页号
	 * 
	 * @return
	 */
	public Long getPageNo();

	/**
	 * 是否第一页
	 * 
	 * @return
	 */
	public boolean isFirstPage();

	/**
	 * 是否最后一页
	 * 
	 * @return
	 */
	public boolean isLastPage();

	/**
	 * 返回下页的页号
	 */
	public Long getNextPage();

	/**
	 * 返回上页的页号
	 */
	public Long getPrePage();
}
