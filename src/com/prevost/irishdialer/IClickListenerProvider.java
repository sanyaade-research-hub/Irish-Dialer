package com.prevost.irishdialer;

import android.view.View;

public interface IClickListenerProvider {
	public View.OnClickListener getCallClickListener(final String phoneNumber);
	public View.OnClickListener getSmsClickListener(final String phoneNumber);
}
