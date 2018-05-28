package com.core;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadFile {
	
	private boolean isMultipart;
	//private String filePath;
	private int maxFileSize = 10000 * 1024;
	private int maxMemSize = 4000 * 1024;
	private File file;

	public UploadFile() {
		// TODO Auto-generated constructor stub
		file = null;
	}
	
	public static String getPathOf(ServletContext context, String fileName)
	{
		String filePath = context.getRealPath("/uploads/");
		String diagonal = Factory.for_linux ? "/" : "\\";
		filePath += diagonal;
		return filePath + fileName;
	}
	
	public void uploadFile(HttpServletRequest request,String Pathtemp,String filePath){

		String diagonal = Factory.for_linux ? "/" : "\\";
		filePath += diagonal;
		Pathtemp += diagonal;

		System.out.println(filePath);

		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {

			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(Pathtemp));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator<FileItem> i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					//String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					//String contentType = fi.getContentType();
					//boolean isInMemory = fi.isInMemory();
					//long sizeInBytes = fi.getSize();
					// Write the file
					if (fileName.lastIndexOf(diagonal) >= 0) {
						fileName = filePath + fileName.substring(fileName.lastIndexOf(diagonal));
					} else {
						String id = Global.createRandomID();
						String ext = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
						fileName = filePath + id + ext;
					}
					file = new File(fileName);
					System.out.println(filePath + fileName);
					fi.write(file);

					System.out.println("Uploaded Filename: " + fileName);
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
	
	public String getFileName() {
		return file.getName();
	}
	
	
	

}
