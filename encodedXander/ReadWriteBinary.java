package encodedXander;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Author: Alexander Pereira MARCH 2015, Croydon, UK
 * 
 * Util Class that reads in a file using a buffer, converts based on a map using VowelsCased another class and then writes output to OutPut file
 * 
 */
public class ReadWriteBinary {
	
	private void readWriteFile(String filepath, int bufsize, String fileOutpath, VowelsCased vowutil)
	{
		
		File f = null;
		File fout = null;
		
		FileInputStream fs = null ;
		FileOutputStream fso = null ;
		
		BufferedInputStream bis = null ;
		BufferedOutputStream bos = null ;
		
		byte[] readbuf = new byte[bufsize];
		StringBuffer sb = new StringBuffer(new String(readbuf));
		String s = null ;
		boolean flagRun = true ;
		
		try
		{
			
			f = new File (filepath);
			fs = new FileInputStream( f );
			bis = new BufferedInputStream( fs ) ;
			
			fout = new File(fileOutpath);
			fso = new FileOutputStream(fout);
			bos = new BufferedOutputStream(fso);
			
			long start = System.currentTimeMillis() ;
			System.out.println("Conversion starting for " + f.getName()+ " ....") ;
			while (flagRun)
			{
				int nobytesRead = bis.read(readbuf) ;
				if ( nobytesRead == -1 )
				{
					flagRun = false ;
				}
				else
				{
					//System.out.println("nobytesRead=" + nobytesRead);
					s = new String(readbuf) ;
					if (nobytesRead == bufsize)
					{
						sb.replace( 0, nobytesRead  , s);
					}
					else
					{
						sb.replace( 0, nobytesRead  , s);
						sb = new StringBuffer(sb.substring(0, nobytesRead));
						
					}
					
					String prnt = vowutil.convert(sb.toString());
					bos.write(prnt.getBytes());
					//System.out.print(prnt); // debug print - shows the converted buffer characters
				}
				
				bos.flush();
			}
			long end = System.currentTimeMillis();
			System.out.println("Conversion Finished for " + fout.getName()+" time taken is MilliSeconds=" + (end - start));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (null != bis)
				{
					bis.close();
					fs.close();
				}
				
				if (null != bos)
				{
					bos.close();
					fso.close();
				}
				
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
		
		try
		{
			VowelsCased vowutil = new VowelsCased();
						
			// the vowels
			vowutil.addRule("a","A");
			vowutil.addRule("e","E");
			vowutil.addRule("i","I");
			vowutil.addRule("o","O");
			vowutil.addRule("u","U");
			
			// adding some other rule
			vowutil.addRule("9", "@"); // this opposes the USE CASE where these mappings are not automatic proper OPP cases !!!!
			vowutil.addRule("8", "Y");
			vowutil.addRule(",","|");
			
			// removing some rules 
			vowutil.removeRule("Y");
			
			vowutil.printRules();
	
			ReadWriteBinary rwb = new ReadWriteBinary();
			rwb.readWriteFile("C:\\temp\\file1IN.csv",8192,"C:\\temp\\file1OUT.csv",vowutil);
		}
		catch (Exception ve)
		{
			ve.printStackTrace();
		}
	}
}





/*
StringBuffer tmp = new StringBuffer("01234567890123456789");
System.out.println(tmp.length());
tmp.replace(0, tmp.length(),      "ABCDEFGHIJabcdefghij");
System.out.println(tmp);
System.out.println(tmp.length());
*/
/*		String v = "0123456789" ;
System.out.println(v.substring(0,4)); // 0123
*/
