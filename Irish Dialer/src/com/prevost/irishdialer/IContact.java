package com.prevost.irishdialer;

import java.util.ArrayList;

public interface IContact {
	public ArrayList<String> getFields();
	public String getFullName();
	public String getId();
}
