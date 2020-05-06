package com.example;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class DownloadController {

    // @Autowired
    @RequestMapping("/download-app")
    public String downloadApp() {
        return "download_app";
        // return "Hello! If you are visiting here from your mobile Android phone, please click <a href='download_object.pdf' download>here</a> to download the app to your phone.";
    }

}