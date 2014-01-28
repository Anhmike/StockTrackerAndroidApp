/*
 * @(#)FileView.java	1.6 05/05/15
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

import java.awt.*;
import java.beans.*;
import java.net.*;
import javax.activation.*;

public class FileView {
    private Frame frame;

    public static void main(String args[]) throws Exception {
        FileView fv = new FileView();
        if (args.length == 0) {
            System.out.println("usage: FileView  file.txt");
            System.exit(1);
        }
        fv.view(args[0]);
    }

    private void view(String filename) throws Exception {
  	FileDataSource fds = new FileDataSource(filename); 
  	DataHandler dh = new DataHandler(fds); 
	// comment out previous two lines, and uncomment next
	// line and pass in a URL on the command line.
	// DataHandler dh = new DataHandler(new URL(filename));

	CommandInfo bi = dh.getCommand("view");
	
	if (bi == null) {
	    System.out.println("no viewer found, exiting");
	    System.exit(1);
	}

	frame = new Frame("Viewer");
	frame.add((Component)dh.getBean(bi));
	frame.setSize(new Dimension(400,300));
	frame.show();
    }
}
