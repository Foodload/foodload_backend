package se.foodload.repository.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class OffsetLimitPageable implements Pageable, Serializable {

	private static final long serialVersionUID = 8072299013284692679L;
	
	private int limit;
    private long offset;
    private final Sort sort;

    public OffsetLimitPageable(int offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetLimitPageable(long offset, int limit) {
    	if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.offset = offset;
        this.limit = limit;
        this.sort = Sort.by(Sort.Direction.DESC,"id");
    }

    @Override
    public int getPageNumber() {
        return (int) ((int) offset / limit);
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPageable(getOffset() + getPageSize(), getPageSize());
    }

    public OffsetLimitPageable previous() {
        return hasPrevious() ? new OffsetLimitPageable(getOffset() - getPageSize(), getPageSize()) : this;
    }


    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPageable(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OffsetLimitPageable)) return false;

        OffsetLimitPageable that = (OffsetLimitPageable) o;

        if(limit == that.limit && offset == that.offset && sort == that.sort)
        	return true;
        else
        	return false;
    }
}