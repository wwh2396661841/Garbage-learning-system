package com.wwh.system;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@MapperScan("com.wwh.**.mapper")
@Slf4j
public class WwhSystemApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(WwhSystemApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n .----------------------------------------------------------------.\n" +
                "| .--------------------------------------------------------------. |\n" +
                "| |                                                              | |\n" +
                "| |  Application User-Center is running! Access URLs:            | |\n" +
                "| |       Local: http://localhost:" + port + path + "/           | |\n" +
                "| |    External: http://" + ip + ":" + port + path + "/          | |\n" +
                "| |  Swagger文档: http://" + ip + ":" + port + path + "/doc.html  | |\n" +
                "| |                                                              | |\n" +
                "| '--------------------------------------------------------------' |\n" +
                " '----------------------------------------------------------------' ");
    }
}
