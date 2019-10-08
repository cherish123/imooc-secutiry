package com.imooc.core.validate.code.image;

import com.imooc.core.validate.code.AbStractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbStractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(),"JPEG",request.getResponse().getOutputStream());
    }
}