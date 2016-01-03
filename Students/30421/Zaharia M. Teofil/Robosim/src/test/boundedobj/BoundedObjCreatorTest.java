package test.boundedobj;

import org.junit.Assert;
import org.junit.Test;

import robosim.boundedobj.BoundedObjFactory;

public class BoundedObjCreatorTest {

	@Test
	public void test() {
		BoundedObjFactory boc = new BoundedObjFactory();
		boc.constrBoundedObjFrom(new String("0.1, 0.2, 0.3, 0.4, 0.5"));
		
		Assert.fail("not impl");
	}

}
