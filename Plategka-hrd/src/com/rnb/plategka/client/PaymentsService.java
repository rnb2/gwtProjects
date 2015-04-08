package com.rnb.plategka.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.rnb.plategka.data.Payments;

@RemoteServiceRelativePath("paymentsService")
public interface PaymentsService extends RemoteService{
	List<Payments> getPayments(int year);
	Payments addPayment(Payments p);
	void deletePayment(Payments p);
}
