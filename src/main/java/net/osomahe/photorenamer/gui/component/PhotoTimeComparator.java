package net.osomahe.photorenamer.gui.component;

import java.util.Comparator;

import net.osomahe.photorenamer.object.Photo;

public class PhotoTimeComparator implements Comparator<Photo>{

	@Override
	public int compare(Photo p1, Photo p2) {
		Long l1 = p1.getDate().getTimeInMillis();
		Long l2 = p2.getDate().getTimeInMillis();
		if(p1.getCorrection() != null){
			l1 += p1.getCorrection();
		}
		if(p2.getCorrection() != null){
			l2 += p2.getCorrection();
		}
		
		return l1.compareTo(l2);
	}

}
