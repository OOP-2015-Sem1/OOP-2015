package com.solid.d.bad;

import com.solid.i.Invoice;
import com.solid.i.good.GoodInvoicePrinter;

public class BadPrintingService {

	private GoodInvoicePrinter invoicePrinter; // notice the interface!

	public BadPrintingService(GoodInvoicePrinter invoicePrinter) {
		this.invoicePrinter = invoicePrinter;
	}

	public void print(Invoice invoice) {
		invoicePrinter.print(invoice);
	}

}
