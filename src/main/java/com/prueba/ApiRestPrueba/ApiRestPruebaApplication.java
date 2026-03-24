package com.prueba.ApiRestPrueba;

import com.prueba.ApiRestPrueba.util.AesEncryptionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestPruebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestPruebaApplication.class, args);

		//System.out.println("Password encriptada: " +
		//		AesEncryptionUtil.encrypt("7c4a8d09ca3762af61e59520943dc26494f8941b"));
	}
}