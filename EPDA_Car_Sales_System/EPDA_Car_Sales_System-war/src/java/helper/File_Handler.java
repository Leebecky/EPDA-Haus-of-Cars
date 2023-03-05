/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author leebe
 */
public class File_Handler {

    public static String UploadImage(HttpServletRequest req, String fileComponent, String folderName) {
        OutputStream out = null;
        InputStream filecontent = null;

        try {

            /* Receive file uploaded to the Servlet from the HTML5 form */
            Part filePart = req.getPart(fileComponent);
            String fileName = filePart.getSubmittedFileName();

            // Retrieve root path external to webapp
            String rootPath = System.getProperty("catalina.base") + "/docroot/";

            if (rootPath == null || rootPath.isEmpty()) {
                rootPath = "C:/";
            }

            String folderPath = "";
            if (rootPath.equals("C:/")) {
                folderPath = "tmp/";
            }
//            System.out.println("PRJ: " + rootPath);
            folderPath = folderPath.concat("EPDA_Car_Sales_System/uploaded_images/" + folderName);
            String filePath = folderPath + "/" + fileName;

            // Verifying existence
            File folder = new File(rootPath.concat(folderPath));
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Saving file
            out = new FileOutputStream(new File(rootPath + filePath));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            return filePath;

        } catch (Exception ex) {
            System.out.println("FileHandler: UploadImage: " + ex.getMessage());
            return null;
        }
    }

}
