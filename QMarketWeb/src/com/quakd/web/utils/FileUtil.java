package com.quakd.web.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {

	public static final String BLANK_PROFILE_PNG = "/blank_profile.png";
	public static final String PNG_TYPE = "image/png";
	
	public byte[] GetImage() {
	    byte[] data = new byte[0];
	    DataInputStream dis = null;
		try {
			java.net.URL r = this.getClass().getClassLoader().getResource(BLANK_PROFILE_PNG);
			String filePath = r.getFile();	
		    File file = new File(filePath);
		    data = new byte[(int) file.length()];
		    dis = new DataInputStream(new FileInputStream(file));
		    dis.readFully(data);
		    dis.close();
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if(dis != null) {
					dis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	    return data;
	}

	public String GetAppPath()
	{
	    java.net.URL r = this.getClass().getClassLoader().getResource(BLANK_PROFILE_PNG);
	    String filePath = r.getFile();
	    String result = new File(new File(new File(filePath).getParent()).getParent()).getParent();

	    if (!filePath.contains("WEB-INF"))
	    {
	        result = FilenameUtils.concat(result, "WebContent");
	    }

	    return result;
	}
}
