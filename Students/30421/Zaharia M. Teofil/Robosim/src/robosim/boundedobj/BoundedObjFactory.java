package robosim.boundedobj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import robosim.math.Vec2;
import robosim.motion.OrientedPos;

public class BoundedObjFactory {
	public BoundedObjFactory() {
	}

	public BoundedObj constrBoundedObjFrom(String line) {
		String[] stringParam = line.split("[,;]");
		
		double[] param = new double[stringParam.length];
		
		int i = 0;
		for (String stringParamIt : stringParam) {
			param[i] = Double.parseDouble(stringParamIt);
		}
			
		OrientedPos pos = new OrientedPos(new Vec2(param[0], param[1]), param[2], null);
		BoundedObj build = new BoundedObj(pos, param[3], param[4]);
		
		return build;
	}
	
	public BoundedObj[] constrBoundedObjArrFrom(String filename) {
		ArrayList<BoundedObj> boundedObjs = new ArrayList<BoundedObj>();
		
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader buffReader = new BufferedReader(fileReader);
			
			
			while((line = buffReader.readLine()) != null) {
				boundedObjs.add( constrBoundedObjFrom(line) );
			}
			
			buffReader.close();
			
			BoundedObj[] boundedObjArr = new BoundedObj[boundedObjs.size()];
			boundedObjArr = boundedObjs.toArray(boundedObjArr);
			
			return boundedObjArr;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
