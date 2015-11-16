package models.animals;

import com.alexasapps.zoowsome.R;

import java.util.Calendar;

public class Spider extends Insect {

    public Spider() {
        super(7.5, 0.5);
        setName("Black Widow");
        setNrOfLegs(12); // I have no idea
        setCanFly(true);
        setDangerous(false);
    }

    public Spider(String name, boolean canFly, int nrOfLegs, boolean isDangerous, double maintenanceCost,
                  double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setName(name);
        setNrOfLegs(nrOfLegs);
        setCanFly(canFly);
        setDangerous(isDangerous);
    }

    int image = R.drawable.spider;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.Spider);
	}
	*/

    public double getPredisposition() {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        // if(LocalTime.now() > 11 )
        if (hourOfDay > 23 || hourOfDay < 6) {
            return 0.25;
        } else
            return 0;
    }
}
