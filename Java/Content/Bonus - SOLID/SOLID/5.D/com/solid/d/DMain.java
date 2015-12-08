package com.solid.d;

import com.solid.d.bad.BadPrintingService;
import com.solid.d.good.GoodPrintingService;
import com.solid.d.good.HtmlInvoicePrinter;
import com.solid.i.Invoice;
import com.solid.i.good.GoodInvoicePrinter;

public class DMain {

	public static void main(String[] args) {
		testBadD();
		testGoodD();
	}

	private static void testBadD() {
		Invoice invoice = new Invoice(665);

		BadPrintingService badPrintingService = new BadPrintingService(
				new GoodInvoicePrinter());
		badPrintingService.print(invoice);

		// badPrintingService = new BadPrintingService(new HtmlInvoicePrinter()); // but why!?
	}

	private static void testGoodD() {
		Invoice invoice = new Invoice(665);

		GoodPrintingService goodPrintingService = new GoodPrintingService(
				new GoodInvoicePrinter());
		goodPrintingService.print(invoice);

		goodPrintingService = new GoodPrintingService(new HtmlInvoicePrinter());
		goodPrintingService.print(invoice);
	}

}
