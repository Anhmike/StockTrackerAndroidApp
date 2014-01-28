/*
 * @(#)PrefTest.java	1.5 05/05/15
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
import javax.activation.*;

public class PrefTest {

    public static void main(String args[]) {
	MailcapCommandMap mcf = null;

	if (args.length > 0) {
	    try {
		mcf = new MailcapCommandMap(args[0]);
	    } catch (Exception e) {
		e.printStackTrace();
		System.exit(1);
	    }
	} else
	    mcf = new MailcapCommandMap();

	CommandInfo cmdinfo[] = mcf.getAllCommands("text/plain");
	
	if (cmdinfo != null) {
	    System.out.println("ALL Commands for text/plain:");
	    for (int i = 0; i < cmdinfo.length; i++) {
		System.out.println("Verb: " + cmdinfo[i].getCommandName() +
				  " Class: " + cmdinfo[i].getCommandClass());
	    }
	    System.out.println("done");
	} else {
	    System.out.println("no commands");
	}
	System.out.println();

	cmdinfo = mcf.getPreferredCommands("text/plain");
	if (cmdinfo != null) {
	    System.out.println("PREFERRED Commands for text/plain:");
	    for (int i = 0; i < cmdinfo.length; i++) {
		System.out.println("Verb: " + cmdinfo[i].getCommandName() +
				  " Class: " + cmdinfo[i].getCommandClass());
	    }
	    System.out.println("done");
	} else {
	    System.out.println("no commands");
	}
	System.out.println();
    }
}
