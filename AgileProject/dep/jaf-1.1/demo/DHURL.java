/*
 * @(#)DHURL.java	1.4 05/05/15
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

import java.net.*;
import java.io.*;
import javax.activation.*;

public class DHURL {
    URL url = null;
    DataHandler dh = null;
    
    public static void main(String args[]){
        DHURL test = new DHURL();

        if(args.length == 0) {
            System.out.println("usage: DHURL url");
            System.exit(1);
        }

        test.setURL(args[0]);

        test.doit();

    }

    public void setURL(String url) {
	
	try {
	    this.url = new URL(url);
	} catch(MalformedURLException e) {
	    e.printStackTrace();
	    System.out.println("malformed URL!!!");
	    System.exit(1);
	}

    }
    
    public void doit() {
	System.out.print("Creating DataHandler...");
	dh = new DataHandler(url);
	System.out.println("...done.");

	System.out.println("The MimeType of the DH : " +
			   dh.getContentType());
	try {
	InputStream is = dh.getInputStream();
	if(is != null)
	    System.out.println("got an inputstream");
	} catch(Exception e) {
	    e.printStackTrace();
	}

	
    }
	
    
}
