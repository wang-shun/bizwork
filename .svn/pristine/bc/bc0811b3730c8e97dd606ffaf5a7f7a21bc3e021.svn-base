/**
 * 
 */
package com.sogou.bizwork.cas.application.dto;

/**
 *
 */
public class Page {
	
	private Long currentPage = 1L; //当前页,初始为第一页
    private Long pageSize = 20L;; //页面尺寸
    private Long pageCount; //总页面数量
    private Long recordCount; //总记录数
    private Long beforeCount = 0L; //页尺寸*（页数-1）,当前页之前的记录总数
	
	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	public Long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	
	public Long getPageCount() {
		if(pageSize != 0)
		    return (recordCount + pageSize - 1)/pageSize;
	    else
	        return 0L;
	}
	
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	
	public Long getRecordCount() {
		return recordCount;
	}
	
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	
	public Long getBeforeCount() {
		return beforeCount;
	}
	
	public void setBeforeCount(Long beforeCount) {
		this.beforeCount = beforeCount;
	}
}
