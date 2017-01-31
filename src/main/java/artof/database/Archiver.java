package artof.database;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Archiver {

  public Archiver() {
  }

  // copy 'n File
  public void copy(String source, String dest) throws IOException {
	  InputStream input = null;
	  OutputStream output = null;
	  try {
		  input = new FileInputStream(source);
		  output = new FileOutputStream(dest);
		  byte[] buf = new byte[1024];
		  int bytesRead;
		  while ((bytesRead = input.read(buf)) > 0) {
			  output.write(buf, 0, bytesRead);
		  }
	  } finally {
		  input.close();
		  output.close();
	  }
  }

}