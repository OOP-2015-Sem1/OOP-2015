package com.solid.i.bad;

import com.solid.i.ComplexInvoice;
import com.solid.i.Invoice;

public interface BadInvoicePrinter_I {

	void print(Invoice invoice);
	
	void printComplexInvoice(ComplexInvoice complexInvoice);
	
	void someOtherPrintMethod(Invoice invoice);
	
}
