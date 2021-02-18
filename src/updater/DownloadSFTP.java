
package updater;

import java.io.File;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class DownloadSFTP {

    static ChannelSftp channelSftp = null;
    static Session session = null;
    static Channel channel = null;
    static String PATHSEPARATOR = "/";
   
    public void download(int i) {
        //SFTP Server details
        String SFTPHOST = "100.128.51.33"; // SFTP Host Name or SFTP Host IP Address
        int SFTPPORT = 22; // SFTP Port Number
        String SFTPUSER = "root"; // User Name
        String SFTPPASS = "abc123"; // Password
        
        //Editable path variables from here
        String SFTPWORKINGDIR = "/root/Folder"; // Source Directory on SFTP server  **** NEED TO EDIT THIS ****
        String LOCALDIRECTORY = "C:\\Folder\\"; // Local Target Directory
        //To here

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(); // Create SFTP Session
            channel = session.openChannel("sftp"); // Open SFTP Channel
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR); // Change Directory on SFTP Server

            if(i==0)
                channelSftp.get("info.xml", System.getenv("APPDATA") + File.separator + "BlueChip\\info.xml"); //download single file
            else if(i==1)
                recursiveFolderDownload(SFTPWORKINGDIR, LOCALDIRECTORY); // Recursive folder content download from SFTP server

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (channelSftp != null)
                channelSftp.disconnect();
            if (channel != null)
                channel.disconnect();
            if (session != null)
                session.disconnect();
        }
    }
     
    @SuppressWarnings("unchecked")
    private static void recursiveFolderDownload(String sourcePath, String destinationPath) throws SftpException {
        Vector<ChannelSftp.LsEntry> fileAndFolderList = channelSftp.ls(sourcePath); // Let list of folder content

        // Iterate through list of folder content
        for (ChannelSftp.LsEntry item : fileAndFolderList) {

            if (!item.getAttrs().isDir()) { // Check if it is a file (not a directory).
                //if (!(new File(destinationPath + PATHSEPARATOR + item.getFilename())).exists() || (item.getAttrs().getMTime() > Long.valueOf(new File(destinationPath + PATHSEPARATOR + item.getFilename()).lastModified() / (long) 1000).intValue())) 
                //{ // Download only if changed later.

                    new File(destinationPath + PATHSEPARATOR + item.getFilename());
                    channelSftp.get(sourcePath + PATHSEPARATOR + item.getFilename(), destinationPath + PATHSEPARATOR + item.getFilename()); // Download file from source (source filename, destination filename).
                //}
            } 
            else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) {
                new File(destinationPath + PATHSEPARATOR + item.getFilename()).mkdirs(); // Empty folder copy.
                recursiveFolderDownload(sourcePath + PATHSEPARATOR + item.getFilename(), destinationPath + PATHSEPARATOR + item.getFilename()); // Enter found folder on server to read // its contents and create locally.
            }
        }
    }

}
