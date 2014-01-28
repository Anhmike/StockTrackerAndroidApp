/*
 * @(#)MCDump.java	1.2 05/05/15
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

/**
 * Dump out everything we know about a MailcapCommandMap.
 */
public class MCDump {
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

	String[] types = mcf.getMimeTypes();
	if (types == null) {
	    System.out.println("No known MIME types");
	    System.exit(0);
	} else {
	    System.out.println("Known MIME types:");
	    for (int i = 0; i < types.length; i++)
		System.out.println("\t" + types[i]);
	}

	System.out.println();
	System.out.println("All commands for each MIME type:");
	for (int i = 0; i < types.length; i++) {
	    System.out.println("    " + types[i]);
	    CommandInfo[] cmdinfo = mcf.getAllCommands(types[i]);
	    if (cmdinfo == null) {
		System.out.println("\tNONE");
	    } else {
		for (int k = 0; k < cmdinfo.length; k++)
		    System.out.println("\t" + cmdinfo[k].getCommandName() +
			": " + cmdinfo[k].getCommandClass());
	    }
	}

	System.out.println();
	System.out.println("Preferred commands for each MIME type:");
	for (int i = 0; i < types.length; i++) {
	    System.out.println("    " + types[i]);
	    CommandInfo[] cmdinfo = mcf.getPreferredCommands(types[i]);
	    if (cmdinfo == null) {
		System.out.println("\tNONE");
	    } else {
		for (int k = 0; k < cmdinfo.length; k++)
		    System.out.println("\t" + cmdinfo[k].getCommandName() +
			": " + cmdinfo[k].getCommandClass());
	    }
	}

	System.out.println();
	System.out.println("Native commands for each MIME type:");
	for (int i = 0; i < types.length; i++) {
	    System.out.println("    " + types[i]);
	    String[] cmds = mcf.getNativeCommands(types[i]);
	    if (cmds.length == 0) {
		System.out.println("\tNONE");
	    } else {
		for (int k = 0; k < cmds.length; k++)
		    System.out.println("\t" + cmds[k]);
	    }
	}
    }
}
