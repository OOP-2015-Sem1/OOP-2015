package com.solid.i.good;

import com.solid.i.ComplexInvoice;
import com.solid.i.Invoice;

public interface GoodInvoicePrinter_I {

	void print(Invoice invoice);
	
	void printComplexInvoice(ComplexInvoice complexInvoice);
	
}
