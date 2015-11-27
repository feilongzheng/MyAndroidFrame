package com.lcworld.fitness.framework.util;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务器数据DECODE
 * @author HanlionTien
 *
 */
public class IOUtils {
	public  static String  toString(InputStream in,String aa){
	        ByteArrayOutputStream  out = new ByteArrayOutputStream (); 
	        try {
				 byte[] b = new byte[1024];  
			        int i = 0;  
			        // inputStream ת byte  
			        while((i = in.read(b,0,1024))>0){  
			            out.write(b,0,i);  
			        }  
			          
			        byte[] req = out.toByteArray();
			        IOUtils.decrypt(req);
			        IOUtils.decode(req);
			        String str = new String(req,"utf-8");  
			        return str;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    return null;
	    
	}
	public  static byte[]  Stringto(String aa){
        try {
			 byte[] b = aa.getBytes("UTF-8");
		        b = IOUtils.encode(b);
		        IOUtils.encryp(b);
		        //String str = new String(b,"utf-8");  
		        
		        return b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    return null;
    
}
	public  static String  toString(byte[] b ){
        try {
        	IOUtils.encryp(b);
		        
            String str = new String(IOUtils.decode(b),"utf-8");

            return str;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    return null;
    
}
	public   static   void encryp(byte [] data)
	 {
	 	for ( int i = 0; i < data.length; i++ )
	 	{
	 		data[i] ^= 0xFF;
	 		//data[i] *= 2;
	 	}
	 }

	public   static   void decrypt(byte [] data)
	 {
	 	for ( int i = 0; i < data.length; i++ )
	 	{
	 		data[i] ^= 0xFF;
	 		//data[i] /= 2;
	 	}
	 }
	 public static byte[] decode(final byte[] bytes) {
		 return Base64.decode(bytes, 0);
	    }
	 public static byte[] encode(final byte[] bytes) {  
	        return Base64.decode(bytes, 0);
	    } 
}
