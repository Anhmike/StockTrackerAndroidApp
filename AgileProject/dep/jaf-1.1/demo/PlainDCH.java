/*
 * @(#)PlainDCH.java	1.9 05/05/15
 *
 * Copyright 1997-2005 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES OR LIABILITIES
 * SUFFERED BY LICENSEE AS A RESULT OF  OR RELATING TO USE, MODIFICATION
 * OR DISTRIBUTION OF THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL
 * SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
import java.io.*;
import java.awt.datatransfer.DataFlavor;
import javax.activation.*;


public class PlainDCH implements DataContentHandler {
    /**
     * return the DataFlavors for this <code>DataContentHandler</code>
     * @return The DataFlavors.
     */
    public DataFlavor[] getTransferDataFlavors() { // throws Exception;
	DataFlavor flavors[] = new DataFlavor[2];
	

	try {
	    flavors[0] = new ActivationDataFlavor(Class.forName("java.lang.String"),
					   "text/plain",
					   "text string");
	} catch(Exception e)
	    { System.out.println(e); }

	flavors[1] = new DataFlavor("text/plain", "Plain Text");
	return flavors;
    }
    /**
     * return the Transfer Data of type DataFlavor from InputStream
     * @param df The DataFlavor.
     * @param ins The InputStream corresponding to the data.
     * @return The constructed Object.
     */
    public Object getTransferData(DataFlavor df, DataSource ds) {
	
	// this is sort of hacky, but will work for the
	// sake of testing...
	if(df.getMimeType().equals("text/plain")) {
	    if(df.getRepresentationClass().getName().equals(
					       "java.lang.String")) {
		// spit out String
		StringBuffer buf = new StringBuffer();
		char data[] = new char[1024];
		// InputStream is = null;
		InputStreamReader isr = null;
		int bytes_read = 0;
		int total_bytes = 0;

		try {
		    isr = new InputStreamReader(ds.getInputStream());
		    
// 		    while(is.read(data) > 0)
// 			buf.append(data);

		    while(true){
			bytes_read = isr.read(data);
			if(bytes_read > 0)
			    buf.append(data, total_bytes, bytes_read);
			else
			    break;
			total_bytes += bytes_read;
		    } 
		} catch(Exception e) {}

		return buf.toString();
		
	    }
	    else if(df.getRepresentationClass().getName().equals(
					     "java.io.InputStream")){
		// spit out InputStream
		try {
		    return ds.getInputStream();
		} catch (Exception e) {}
	    }
		
	} 

	    return null;
    }
    
    /**
     *
     */
    public Object getContent(DataSource ds) { // throws Exception;
	StringBuffer buf = new StringBuffer();
	char data[] = new char[1024];
	// InputStream is = null;
	InputStreamReader isr = null;
	int bytes_read = 0;
	int total_bytes = 0;
	
	try {
	    isr = new InputStreamReader(ds.getInputStream());
	    
	    // 		    while(is.read(data) > 0)
	    // 			buf.append(data);
	    
	    while(true){
		bytes_read = isr.read(data);
		if(bytes_read > 0)
		    buf.append(data, total_bytes, bytes_read);
		else
		    break;
		total_bytes += bytes_read;
	    } 
	} catch(Exception e) {}
	
	return buf.toString();
    }
    /**
     * construct an object from a byte stream
     * (similar semantically to previous method, we are deciding
     *  which one to support)
     */
    public void writeTo(Object obj, String mimeTye, OutputStream os) 
	throws IOException {
	// throws Exception;
    }
    
}
