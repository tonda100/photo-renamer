package net.osomahe.photorenamer.object;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

public class Photo {
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy:MM:dd HH:mm:ss");
	private File file;
	private String name;
	private String newName;
	private String make;
	private String model;
	private Calendar date;
	private Long correction;

	public Photo(File file) {
		this.file = file;
		this.name = file.getName();
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(file);
			Directory exifDirectory = metadata
					.getFirstDirectoryOfType(ExifIFD0Directory.class);
			make = exifDirectory.getString(ExifIFD0Directory.TAG_MAKE);
			model = exifDirectory.getString(ExifIFD0Directory.TAG_MODEL);
			String dateSource = exifDirectory
					.getString(ExifIFD0Directory.TAG_DATETIME);
			if (dateSource != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(format.parse(dateSource));
				date = cal;
			}else{
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(file.lastModified());
				date = cal;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCamera() {
		if(make != null && model != null){
			return String.format("%s (%s)", make, model);
		}else if(make != null){
			return make;
		}else if(model != null){
			return model;
		}else
			return null;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public Long getCorrection() {
		return correction;
	}

	public void setCorrection(Long correction) {
		this.correction = correction;
	}
}
