package com.legocms.core.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装. 注意所有序号从1开始.
 */
@XmlRootElement(name = "page")
public class Page<T> extends Dto {

    private static final long serialVersionUID = 8204782953867306849L;

    // -- 分页参数 --//
    protected int current = 1; // 当前页
    protected int pageSize = -1; // 每页显示条数
    protected boolean autoCount = true; // 是否计算总记录条数

    // -- 返回结果 --//
    protected List<T> result = new ArrayList<T>();
    protected long totalCount = -1; // 总记录条数
    protected long totalPage = -1; // 总页数

    // -- 构造函数 --//
    public Page() { }

    public Page(final int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(final List<T> result, final int current, final int pageSize, final long totalCount) {
        super();
        this.current = current;
        this.pageSize = pageSize;
        this.result = result;
        this.totalCount = totalCount;
        if (totalCount == 0) {
            this.totalPage = 1;
        }
        else if (totalCount % pageSize == 0) {
            this.totalPage = totalCount / pageSize;
        }
        else {
            this.totalPage = totalCount / pageSize + 1;
        }
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(final int current) {
        this.current = current;
        if (current < 1) {
            this.current = 1;
        }
    }

    public Page<T> pageCurrent(final int current) {
        setCurrent(current);
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public Page<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    public int getFirst() {
        return ((current - 1) * pageSize) + 1;
    }

    public boolean isAutoCount() {
        return autoCount;
    }

    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(final boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(final List<?> result) {
        this.result = (List<T>) result;
    }

    public void addResult(final T result) {
        this.result.add(result);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }
        return (totalCount + pageSize - 1) / pageSize;
    }

    public long getTotalPage() {
        if (totalCount < 0) {
            return -1;
        }
        return (totalCount + pageSize - 1) / pageSize;
    }

    public void setTotalPage(final long totalPage) {
        this.totalPage = totalPage;
    }
}
