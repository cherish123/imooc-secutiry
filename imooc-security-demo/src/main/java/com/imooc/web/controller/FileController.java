package com.imooc.web.controller;

import com.imooc.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    private String folder = "E:\\ideaProjects\\imooc-security\\imooc-security-demo\\src\\main\\java\\com\\imooc\\web\\controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {

        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(folder,new Date().getTime()+".txt");

        file.transferTo(localFile);//如果这边不用transfer也可以使用file.getInputStream();来得到一个输入流

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //jdk1.7之后将各种流声明在try括号中，会自动关闭，不必去手动关闭
        try(InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
            OutputStream outputStream = response.getOutputStream();){

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=test.txt");

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();

        }
    }
}
