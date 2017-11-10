package com.sogou.bizwork.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Page<T> {
	public static final int DEFAULT_PAGE_SIZE = 20;

	private List<T> records = Collections.emptyList();

	private int pageSize = 20;

	public int getPageCount() {
		int n = records.size() / pageSize;
		return n > 0 ? n + 1 : 1;
	}

	public void sort(Comparator<T> c) {
		Collections.sort(records, c);
	}

	public List<T> getPage(int pageNo) {
		int pageCount = getPageCount();
		if (pageNo < 0 || pageNo > pageCount) {
			return Collections.emptyList();
		}
		if (pageCount == 1 && pageNo == 1) {
			return records;
		}
		if (pageCount > 1) {
			if (pageCount != pageNo) {
				return records.subList((pageNo - 1) * pageSize, pageNo * pageSize);
			} else {
				return records.subList((pageNo - 1) * pageSize, records.size());
			}
		}
		return Collections.emptyList();
	}
	
	/**
	 * 若输入的页数大于最大页数，则显示最后一页内容
	 * @param pageNo
	 * @return
	 */
	public List<T> getPageByTruncate(int pageNo) {
		int pageCount = getPageCount();
		if (pageNo < 0) {
			return Collections.emptyList();
		}
		//页数过大时，显示最后一页内容
		if (pageNo > pageCount) {
			pageNo = pageCount;
		}
		if (pageCount == 1 && pageNo == 1) {
			return records;
		}
		if (pageCount > 1) {
			if (pageCount != pageNo) {
				return records.subList((pageNo - 1) * pageSize, pageNo * pageSize);
			} else {
				return records.subList((pageNo - 1) * pageSize, records.size());
			}
		}
		return Collections.emptyList();
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page() {

	}

	public Page(List<T> records, int pageSize) {
		this.records = records;
		this.pageSize = pageSize;
	}
}