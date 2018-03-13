package com.cwoongc.tess4jtest;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class Tess4jtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tess4jtestApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			File imgFile = new File(getClass().getResource("image007.png").getFile());

			System.out.println(imgFile.isFile());
			System.out.println(imgFile.getAbsolutePath());
			System.out.println(imgFile.getPath());

			ITesseract instance = new Tesseract();

			try {
				instance.setLanguage("kor");

				String result = instance.doOCR(imgFile);
				System.out.println(result);
			} catch (TesseractException e) {
				System.err.println(e.getMessage());
			}

		};
	}
}
