package com.solid.o;

import com.solid.o.bad.BadClient;
import com.solid.o.bad.BadServer;
import com.solid.o.good.Client_I;
import com.solid.o.good.GoodClient;
import com.solid.o.good.GoodServer;

public class OMain {

	public static void main(String[] args) {
		testBadO();
		testGoodO();
	}

	private static void testBadO() {
		BadClient client = new BadClient();
		BadServer server = new BadServer();

		server.reactToClient(client);
	}

	private static void testGoodO() {
		Client_I client = new GoodClient();
		GoodServer server = new GoodServer();
		
		server.reactToClient(client);
	}

}
