package com.example.sugarroad2.controller;

import com.example.sugarroad2.util.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImageController {
	@Autowired
	ImageUtil imageUtil;
	@GetMapping("/images/**")
	@ResponseBody
	public byte[] readImage(HttpServletRequest req) throws IOException {
		return imageUtil.readImage(req.getRequestURI().substring(7));
	}

	@GetMapping("/test/images")
	public ModelAndView testImage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/test/image");
		mav.addObject("imageSrc", "/images/test.png");
		return mav;
	}

}
