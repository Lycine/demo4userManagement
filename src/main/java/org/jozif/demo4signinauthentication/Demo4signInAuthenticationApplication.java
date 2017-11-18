package org.jozif.demo4signinauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author hongyu 2017-11-18
 */
@ServletComponentScan
@SpringBootApplication
public class Demo4signInAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo4signInAuthenticationApplication.class, args);
	}
}
