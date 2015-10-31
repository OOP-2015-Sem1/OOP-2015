package javasmmr.zoowsome.models.animals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Calendar;

public class Tiger extends Mammal 
{
	public Tiger()
	{
		super(7.0,0.75);
		this.setName("Muriel");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(40.6f);
		this.setPercBodyHair(85.6f);
	}

	public Tiger(String name, int nrOfLegs, float normalBodyTemp, float percBodyHair)
	{
		super(7.0,0.75);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}

	@Override
	public double getPredisposition()  {

		String  startOfTimeInterval = "16:00:00";
		Date startTime = null;
		try {
			startTime = new SimpleDateFormat("hh:mm:ss").parse(startOfTimeInterval);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String finishOfTimeInterval = "19:00:00";
		Date stopTime = null;
		try {
			stopTime = new SimpleDateFormat("hh:mm:ss").parse(finishOfTimeInterval);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		Calendar firstCalendar = GregorianCalendar.getInstance();
		Calendar secondCalendar = GregorianCalendar.getInstance();
		firstCalendar.setTime(startTime);
		secondCalendar.setTime(stopTime);
		Calendar currentCalendar = GregorianCalendar.getInstance();

		if(( currentCalendar.get(Calendar.HOUR) > firstCalendar.get(Calendar.HOUR) ) && ( currentCalendar.get(Calendar.HOUR) < secondCalendar.get(Calendar.HOUR)))
		{
			return 0.1;
		}
		if(( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR) ) || ( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)))
		{
			if( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR)  && currentCalendar.get(Calendar.MINUTE) > firstCalendar.get(Calendar.MINUTE)  )
			{
				return 0.1;
			}
			if( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)  &&  currentCalendar.get(Calendar.MINUTE) < secondCalendar.get(Calendar.MINUTE))
			{
				return 0.1;
			}
		}
		if((( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR) ) || ( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)))&&((( currentCalendar.get(Calendar.MINUTE) == firstCalendar.get(Calendar.MINUTE) ) || ( currentCalendar.get(Calendar.MINUTE) == secondCalendar.get(Calendar.MINUTE)))))
		{
			if( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR)  && currentCalendar.get(Calendar.MINUTE) == firstCalendar.get(Calendar.MINUTE)  && currentCalendar.get(Calendar.SECOND) > firstCalendar.get(Calendar.SECOND) )
			{
				return 0.1;
			}
			if( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)  && secondCalendar.get(Calendar.MINUTE) == firstCalendar.get(Calendar.MINUTE)  && currentCalendar.get(Calendar.SECOND) < secondCalendar.get(Calendar.SECOND) )
			{
				return 0.1;
			}
		}
		
		return 0;

	}
}
