/*
 * @(#)MCTest.java	1.8 05/05/15
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
import java.beans.*;
import com.sun.activation.registries.*;
import javax.activation.*;

public class MCTest {
    static MailcapCommandMap mcf = null;

    public static void main(String args[]) {
	
	try {
	    if (args.length == 0)
		mcf = new MailcapCommandMap();
	    else
		mcf = new MailcapCommandMap(args[0]);
	} catch (Exception e){ 
	    e.printStackTrace();
	    System.exit(1);
	}

	CommandInfo cmdinfo[] = mcf.getAllCommands("text/plain");
        System.out.print("Are there any commands for text/plain?");

	if (cmdinfo != null) {
	    System.out.println("number of cmds = " + cmdinfo.length);
	    System.out.println("now try an individual cmd");
	    CommandInfo info = mcf.getCommand("text/plain", "view");
	    if (info != null) {
		System.out.println("Got command...");
	    } else {
		System.out.println("no cmds");
	    }

	    mcf.addMailcap("text/plain;; x-java-flobotz=com.sun.activation.flobotz\n");	
	    //	    System.out.println("...dome");
	    if (cmdinfo != null) {
		cmdinfo = mcf.getAllCommands("text/plain");
		System.out.println("now we have cmds = " + cmdinfo.length);
		
	    }	

        } else {
	    System.out.println("NO CMDS AT ALL!");
	}
    }
}
