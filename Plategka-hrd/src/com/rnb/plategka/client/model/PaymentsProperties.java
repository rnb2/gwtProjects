package com.rnb.plategka.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb.plategka.data.Payments;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface PaymentsProperties extends PropertyAccess<Payments> {

	@Path(value = "id")
	ModelKeyProvider<Payments> id();

	/** #1: ���������: */
	ValueProvider<Payments, Double> pay1();

	/** #2: ��� */
	ValueProvider<Payments, Double> pay2();

	/** ��� #3 ����� */
	ValueProvider<Payments, Double> pay3();

	/** ��� #3 ��������� */
	ValueProvider<Payments, Integer> pay3_1();

	/** ��� #3 �������� */
	ValueProvider<Payments, Integer> pay3_2();

	/** ���� #4 ����� */
	ValueProvider<Payments, Double> pay4();

	/** ���� #4 ��������� */
	ValueProvider<Payments, Integer> pay4_1();

	/** ���� #4 �������� */
	ValueProvider<Payments, Integer> pay4_2();

	/** ���� #4 ����� <150 kBt */
	ValueProvider<Payments, Double> pay4Less();

	/** ���� #4 ����� >150 kBt */
	ValueProvider<Payments, Double> pay4More();

	/** ������� #5 */
	ValueProvider<Payments, Double> pay5();

	/** ����� #6 */
	ValueProvider<Payments, Double> pay6();

	/** ������� */
	ValueProvider<Payments, Double> pay7();

	/** ��� ������ */
	ValueProvider<Payments, Integer> yearOfPay();

	/** ���� ������ */
	ValueProvider<Payments, String> dateOfPay();
}
