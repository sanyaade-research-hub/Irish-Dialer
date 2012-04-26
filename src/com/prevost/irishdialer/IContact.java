package com.prevost.irishdialer;

import java.util.List;

public interface IContact {
	public List<String> getFields();
	public String getFullName();
	public String getId();
}
