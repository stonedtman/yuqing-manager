package com.stonedt;

import com.stonedt.yuqingwechat.EnableWechat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableWechat
public class YqAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(YqAdminApplication.class, args);
	}

}
