package com.rnb.plategka.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb.plategka.data.Payments;

public interface PaymentsServiceAsync {

	void getPayments(int year, AsyncCallback<List<Payments>> callback);

	void addPayment(Payments p, AsyncCallback<Payments> callback);

	void deletePayment(Payments p, AsyncCallback<Void> callback);

}
