package cn.onlov.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "cn.onlov.admin.core.dao.mapper")
@EnableTransactionManagement
@EnableScheduling
public class OnAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnAdminApplication.class, args);
    }
}
