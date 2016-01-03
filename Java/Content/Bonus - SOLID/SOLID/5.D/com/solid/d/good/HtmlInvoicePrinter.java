package com.solid.d.good;

import com.solid.i.ComplexInvoice;
import com.solid.i.Invoice;
import com.solid.i.good.GoodInvoicePrinter_I;

public class HtmlInvoicePrinter implements GoodInvoicePrinter_I {

	@Override
	public void print(Invoice invoice) {
		System.out.println("Printing invoice " + invoice + " to HTML");
	}

	@Override
	public void printComplexInvoice(ComplexInvoice complexInvoice) {
		System.out.println("Printing complex invoice " + complexInvoice
				+ " to HTML");
	}

}
