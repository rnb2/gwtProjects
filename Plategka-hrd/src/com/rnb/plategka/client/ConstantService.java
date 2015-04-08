package com.rnb.plategka.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.rnb.plategka.data.Constants;
import com.rnb.plategka.data.ConstantsProxy;

@RemoteServiceRelativePath("constantService")
public interface ConstantService extends RemoteService{
		List<Constants> getConstants();
		List<ConstantsProxy> getConstantsProxy();
		Constants addConstant(Constants c);
		void delete(Constants c);
		Constants saveConstant(Constants c);
		int getYear(Date date);
		List<Integer> getYears();
		void delete(Long c);
}
