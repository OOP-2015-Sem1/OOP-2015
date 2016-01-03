package robosim.physics.collision;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import robosim.boundedobj.BoundedObj;

public class ShapeFactory {
	ShapeFactory() {
		
	}
	
	Shape createShapeFrom(BoundedObj rect) {
		AffineTransform t = new AffineTransform();
		t.translate(
				rect.getX() - rect.getWidth()/2, 
				rect.getY() - rect.getHeight()/2);
		t.rotate(rect.getTheta());
		
		Rectangle2D.Double srcRect = new Rectangle2D.Double(
				0, 0, rect.getWidth(), rect.getHeight());
		
		Shape destShape = t.createTransformedShape(srcRect);
		return t.createTransformedShape(destShape);
	}
	
	Shape[] createShapeArrFrom(BoundedObj[] rects) {
		Shape[] shapeArr = new Shape[rects.length];
		
		int i = 0;
		for (BoundedObj rect : rects) {
			shapeArr[i] = createShapeFrom(rect);
			++i;
		}
		
		return shapeArr;
	}
	
}
