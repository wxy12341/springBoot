package com.example.test.redisConfig;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfiguration {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();//是否有边框
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "red");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "1");
        properties.put("kaptcha.textproducer.char.length", "4");//验证码文本字符长度  默认为5
        properties.put("kaptcha.image.width", "120");
        properties.put("kaptcha.image.height", "50");
        properties.put("kaptcha.textproducer.font.size", "40");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

