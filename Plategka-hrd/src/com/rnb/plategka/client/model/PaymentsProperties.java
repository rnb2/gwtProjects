package com.rnb.plategka.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb.plategka.data.Payments;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface PaymentsProperties extends PropertyAccess<Payments> {

	@Path(value = "id")
	ModelKeyProvider<Payments> id();

	/** #1: Теплосеть: */
	ValueProvider<Payments, Double> pay1();

	/** #2: Газ */
	ValueProvider<Payments, Double> pay2();

	/** ВКХ #3 сумма */
	ValueProvider<Payments, Double> pay3();

	/** ВКХ #3 начальные */
	ValueProvider<Payments, Integer> pay3_1();

	/** ВКХ #3 конечные */
	ValueProvider<Payments, Integer> pay3_2();

	/** Свет #4 сумма */
	ValueProvider<Payments, Double> pay4();

	/** Свет #4 начальные */
	ValueProvider<Payments, Integer> pay4_1();

	/** Свет #4 конечные */
	ValueProvider<Payments, Integer> pay4_2();

	/** Свет #4 сумма <150 kBt */
	ValueProvider<Payments, Double> pay4Less();

	/** Свет #4 сумма >150 kBt */
	ValueProvider<Payments, Double> pay4More();

	/** КвартПл #5 */
	ValueProvider<Payments, Double> pay5();

	/** Мусор #6 */
	ValueProvider<Payments, Double> pay6();

	/** Домофон */
	ValueProvider<Payments, Double> pay7();

	/** Год оплаты */
	ValueProvider<Payments, Integer> yearOfPay();

	/** Дата оплаты */
	ValueProvider<Payments, String> dateOfPay();
}
