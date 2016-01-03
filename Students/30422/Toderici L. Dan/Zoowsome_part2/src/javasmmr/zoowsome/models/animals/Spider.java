package javasmmr.zoowsome.models.animals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Spider extends Insect 
{

	 public Spider() 
	 {
		 super(1.5,0.45);
		 setNrOfLegs(8);
		 setName("Neal");
		 setCanFly(false);
		 setIsDangerous(true);
	 }
	 
	 public Spider(String name) 
	 {
		 super(1.5,0.45);
		 setNrOfLegs(8);
		 setName(name);
		 setCanFly(false);
		 setIsDangerous(true);
	 }
	 
		@Override
		public double getPredisposition()  {

			String  startOfTimeInterval = "12:00:00";
			Date startTime = null;
			try {
				startTime = new SimpleDateFormat("hh:mm:ss").parse(startOfTimeInterval);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String finishOfTimeInterval = "13:58:00";
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
			
//			Date currentDate = new Date();
//			SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy EEE hh:mm:ss");
//			String dateString = s.format(currentDate);
//			System.out.println(dateString);
			

			if(( currentCalendar.get(Calendar.HOUR) > firstCalendar.get(Calendar.HOUR) ) && ( currentCalendar.get(Calendar.HOUR) < secondCalendar.get(Calendar.HOUR)))
			{
				return 0.25;
			}
			if(( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR) ) || ( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)))
			{
				if( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR)  && currentCalendar.get(Calendar.MINUTE) > firstCalendar.get(Calendar.MINUTE)  )
				{
					return 0.25;
				}
				if( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)  &&  currentCalendar.get(Calendar.MINUTE) < secondCalendar.get(Calendar.MINUTE))
				{
					return 0.25;
				}
			}
			if((( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR) ) || ( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)))&&((( currentCalendar.get(Calendar.MINUTE) == firstCalendar.get(Calendar.MINUTE) ) || ( currentCalendar.get(Calendar.MINUTE) == secondCalendar.get(Calendar.MINUTE)))))
			{
				if( currentCalendar.get(Calendar.HOUR) == firstCalendar.get(Calendar.HOUR)  && currentCalendar.get(Calendar.MINUTE) == firstCalendar.get(Calendar.MINUTE)  && currentCalendar.get(Calendar.SECOND) > firstCalendar.get(Calendar.SECOND) )
				{
					return 0.25;
				}
				if( currentCalendar.get(Calendar.HOUR) == secondCalendar.get(Calendar.HOUR)  && secondCalendar.get(Calendar.MINUTE) == firstCalendar.get(Calendar.MINUTE)  && currentCalendar.get(Calendar.SECOND) < secondCalendar.get(Calendar.SECOND) )
				{
					return 0.25;
				}
			}
			
			return 0;

		}
}
