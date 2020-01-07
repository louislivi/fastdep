package com.louislivi.fastdep.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Fast dep file servlet
 *
 * @author : louislivi
 */
public class FastDepFileServlet extends HttpServlet {

    private FastDepFileProperties fastDepFileProperties;

    public FastDepFileServlet(FastDepFileProperties fastDepFileProperties) {
        this.fastDepFileProperties = fastDepFileProperties;
    }

    /**
     * Fast dep file service
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String requestUri = request.getRequestURI();
        FileStorageUtil fileStorageUtil = new FileStorageUtil(this.fastDepFileProperties);
        String downloadUrl = this.fastDepFileProperties.getDownloadUrl();
        if (this.fastDepFileProperties.getUploadUrl().equals(requestUri)) {
            // HttpServletRequest to MultipartHttpServletRequest
            StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
            MultipartFile files = multipartRequest.getFile(this.fastDepFileProperties.getFieldName());
            FileResponse fileResponse = fileStorageUtil.uploadFile(files);
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(fileResponse);
            out.println(result);
            out.flush();
            out.close();
        } else if (requestUri.startsWith(downloadUrl)) {
            // download file
            fileStorageUtil.downloadFile(requestUri.substring(downloadUrl.length() + 1), request, response);
        }
    }
}
