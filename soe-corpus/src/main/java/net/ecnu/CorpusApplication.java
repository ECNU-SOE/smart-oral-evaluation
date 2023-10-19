package net.ecnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("net.ecnu.mapper")
@EnableTransactionManagement
@EnableFeignClients
@EnableScheduling //允许自动调度
@EnableDiscoveryClient
@SpringBootApplication
public class CorpusApplication {
    public static void main(String[] args) {
        SpringApplication.run(CorpusApplication.class, args);
    }
}