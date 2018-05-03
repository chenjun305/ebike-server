package com.ecgobike;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by ChenJun on 2018/5/3.
 */
@EnableScheduling
@SpringBootApplication
public class EbikeServerTask {
    public static void main(String[] args) {
        SpringApplication.run(EbikeServerTask.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Thread.currentThread().join();
//    }
}
