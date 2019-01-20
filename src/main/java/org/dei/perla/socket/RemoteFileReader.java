package org.dei.perla.socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;



public class RemoteFileReader extends Thread{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	//http://cnt.rm.ingv.it/feed/atom/all_week
	
	private int per;
	
	
	public RemoteFileReader(String rssFeedUrl, Integer period){
		
	}
	
	public void setPeriod(int per){
		this.per=per;
	}
	
public RemoteFileReader(){
		
	}
	
	public void remoteReader() {
	
		while(true){
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
		
		InputStream in;
		try {
			in = new URL( "http://cnt.rm.ingv.it/feed/atom/all_week" ).openStream();
			
			FileWriter w;
		    w=new FileWriter("backupIngv"+timeStamp+".xml");

		    BufferedWriter b;
		    b=new BufferedWriter (w);
		    
			InputStreamReader inR = new InputStreamReader( in );
			BufferedReader buf = new BufferedReader( inR );

			String line;

			while ( ( line = buf.readLine() ) != null ) {

				b.write(line+"\n");

			}
			
			b.flush();
			
			
			try {
				XMLParser.xmlParser("backupIngv"+timeStamp+".xml");
			} catch (ParserConfigurationException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		try {
			
			int timeToSleep=per*60*1000;
			
		    Thread.sleep(timeToSleep);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		}


	}

	@Override
	public void run() {
		this.remoteReader();
		
	}



}
