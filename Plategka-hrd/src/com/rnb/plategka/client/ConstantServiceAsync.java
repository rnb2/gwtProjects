package com.rnb.plategka.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb.plategka.data.Constants;
import com.rnb.plategka.data.ConstantsProxy;

public interface ConstantServiceAsync {

	void getConstants(AsyncCallback<List<Constants>> callback);

	void addConstant(Constants c, AsyncCallback<Constants> callback);

	void delete(Constants c, AsyncCallback<Void> callback);

	void saveConstant(Constants c, AsyncCallback<Constants> callback);

	void getYear(Date date, AsyncCallback<Integer> callback);

	void getYears(AsyncCallback<List<Integer>> callback);

	void getConstantsProxy(AsyncCallback<List<ConstantsProxy>> callback);

	void delete(Long c, AsyncCallback<Void> callback);
	
}
