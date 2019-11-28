/*
 * Copyright (c) 2017. KOTALL Team, http://www.kotall.com/
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.github.aracwong.commons.pagination;

/**
 * Pagination
 * @author : aracwong
 */
public class SimplePage implements Pageable {
    public static final int DEF_COUNT = 20;
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected int totalCount = 0;

    public SimplePage() {
    }

    public SimplePage(int pageNo, int pageSize, int totalCount) {
        this.setTotalCount(totalCount);
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
        this.adjustPageNo();
    }

    public void adjustPageNo() {
        if (pageNo < 1) {
            pageNo = 1;
            return;
        }
        if (pageNo == 1) {
            return;
        }
        int tp = getTotalPage();
        if (pageNo > tp) {
            pageNo = tp;
        }
    }

	/*
     * public static int cpn(Integer pageNo) { return (pageNo == null || pageNo < 1) ? 1 : pageNo; }
	 *
	 */
    @Override
    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public int getTotalPage() {
        int totalPage = totalCount / pageSize;
        if (totalPage == 0 || totalCount % pageSize != 0) {
            totalPage++;
        }
        return totalPage;
    }

    @Override
    public int getPageSize() {

        return pageSize;
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public boolean isFirstPage() {

        return pageNo <= 1;
    }

    @Override
    public boolean isLastPage() {

        return pageNo >= getTotalPage();
    }

    @Override
    public int getPrePage() {
        if (isFirstPage()) {
            return pageNo;
        } else {
            return pageNo - 1;
        }

    }

    @Override
    public int getNextPage() {
        if (isLastPage()) {
            return pageNo;
        } else {
            return pageNo + 1;
        }

    }

    public void setTotalCount(int totalCount) {
        if (totalCount < 0) {
            this.totalCount = 0;
        } else {
            this.totalCount = totalCount;
        }
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            this.pageSize = DEF_COUNT;
        } else {
            this.pageSize = pageSize;
        }
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }

}
