/*
 * @(#)SimpleDCF.java	1.3 05/05/15
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

import javax.activation.*;
import java.util.StringTokenizer;
import java.util.Hashtable;

public class SimpleDCF implements DataContentHandlerFactory {
    Hashtable entry_hash = new Hashtable();
    /**
     * the constructor, takes a list of classes as an argument in the
     * form:
     * <mimetype>:<class name>\n
     *
     * For Example:
     *
     * application/x-wombat:com.womco.WombatDCH
     * text/plain:com.textco.TextDCH
     *
     */
    public SimpleDCF(String entries) {
	StringTokenizer tok = new StringTokenizer(entries);

	String entry;
	System.out.println("SimpleDCH: new SimpleDCF being created");

	// parse the string
	while(tok.hasMoreTokens()) {
	    int colon;

	    entry = tok.nextToken();
	    System.out.println("full entry = " + entry);

	    // parse out the fields
	    colon = entry.indexOf(':');
	    VectorEntry ve = new VectorEntry(entry.substring(0,colon),
					     entry.substring(colon + 1, 
							     entry.length()));
	    System.out.println("adding element = " + ve);
	    entry_hash.put(ve.getMimeType(),ve);
	}
    }

    /**
     * implement the factor interface
     */
    public DataContentHandler createDataContentHandler(String mimeType){
	DataContentHandler dch = null;

	System.out.print("SimpleDCF: trying to create a DCH");

	VectorEntry ve = (VectorEntry)entry_hash.get(mimeType);
	if(ve != null) {
	    System.out.print("...found token");
	    try { 
		
		dch = (DataContentHandler)Class.forName(
					ve.getClassName()).newInstance();
		if(dch == null)
		    System.out.println("...FAILED!!!");
		else
		    System.out.println("...SUCCESS!!!");

	    } catch(Exception e) {
		System.out.println(e);
	    }
	}
	return dch;
    }
}

class VectorEntry {
    private String mimeType;
    private String className;

    public VectorEntry(String mimeType, String className) {
	this.mimeType = mimeType;
	this.className = className;
    }
    
    public String getMimeType() { return mimeType; }
    public String getClassName() { return className; }
    public String toString() { 
	return new String("type: " + mimeType + " class name: " + className);
    }

}
