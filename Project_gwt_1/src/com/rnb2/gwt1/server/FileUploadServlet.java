package com.rnb2.gwt1.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.rnb2.gwt1.server.utils.Constants;

/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUploadServlet() {
		super();
		//System.out.println("servlet counstr()");
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("do service...");
	
		ServletFileUpload servletFileUpload = new ServletFileUpload();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //System.out.println("Status : "+isMultipart);
		if (isMultipart) {
			InputStream inputStream2 = null;
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletContext servletContext = this.getServletConfig().getServletContext();
				
				//System.out.println(servletContext.getAttribute("javax.servlet.context.tempdir"));
				
				File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(repository);
				servletFileUpload = new ServletFileUpload(factory);
				
				List<FileItem> listFileItems = servletFileUpload.parseRequest(request);
				//System.out.println("listFileItems=" + listFileItems.size());
				for (FileItem fileItem : listFileItems) {
					//System.out.println("fileItem=" + fileItem.getFieldName());
					inputStream2 = fileItem.getInputStream();
				}
				//System.out.println("inputStream2=" + inputStream2);
				
				/*FileItemIterator iter = servletFileUpload.getItemIterator(request);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();

					String name = item.getFieldName();
					InputStream stream = item.openStream();

					System.out.println("name=" + name);

					String fileName = item.getName();
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}
					System.out.println("fileName=" + fileName);

					// Process the input stream
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int len;
					byte[] buffer = new byte[8192];
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						out.write(buffer, 0, len);
					}

					int maxFileSize = 10 * (1024 * 1024); // 10 megs max
					if (out.size() > maxFileSize) {
						throw new RuntimeException("File is > than "
								+ maxFileSize);
					}

					System.out.println("real path="
							+ getServletContext().getRealPath(fileName));

					String uploadDir = System.getProperty("jboss.server.base.dir") + "/uploads";
					String fullFileName = uploadDir	+ "/" + fileName;
					
					System.out.println("File will be Uploaded at: " + fullFileName);

					FileOutputStream fileOutSt = new FileOutputStream(fullFileName);
					fileOutSt.write(out.toByteArray());
					fileOutSt.close();

					System.out.print("File was saved !!!!");
					
					//worked stream from file on disk
					//InputStream inputStream = new FileInputStream(fullFileName);					
					//getServletContext().setAttribute("com.rnb2.gwt1.streamFile.xls", inputStream);
					//--
				}*/
				
				//try 2
				getServletContext().setAttribute(Constants.contextAttributeStreamXlsFile, inputStream2);
			}

			catch (Exception e) {
				//System.out.print("Servlet Exception...");
				e.printStackTrace();
				//System.out.print("Servlet Exception.");
				throw new RuntimeException(e);
			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
