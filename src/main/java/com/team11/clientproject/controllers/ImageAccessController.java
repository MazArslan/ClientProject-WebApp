package com.team11.clientproject.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@Controller
public class ImageAccessController {
    @Value("${files.upload_path}")
    private String path;
    @Value("${files.access_path}")
    private String webPath;

    //reference: https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    // as well as file upload example by carl
    // accessed 03/12/2018
    @RequestMapping(value = "/uploads/{image:.+}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable String image) throws IOException {

        File file = new File(this.path + image);
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        response.setContentType(mimeType);
//        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}
