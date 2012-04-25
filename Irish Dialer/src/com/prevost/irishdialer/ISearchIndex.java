package com.prevost.irishdialer;

import java.util.List;

public interface ISearchIndex<T> {
	public List<T> search(String querystr);	
	public void add(T object);
}
