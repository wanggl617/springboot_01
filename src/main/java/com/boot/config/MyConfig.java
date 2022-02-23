/*
 * Copyright 2021 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.boot.config;

import com.boot.domain.Car;
import com.boot.domain.Pet;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @title 阳光正好，微风不燥
 * @data 2021/11/26
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Car.class)
public class MyConfig {
//1.WebMvcConfigurer 定制化springMVC的功能
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return  new WebMvcConfigurer() {
            //开启矩阵变量
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }
            //自定义converter类型转换器
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Pet>() {
                    //{name,age}
                    @Override
                    public Pet convert(String source) {
                        if(source.length()!=0 && !"".equals(source)){
                            String[] new_pet = source.split(",");
                            Pet pet = new Pet();
                            pet.setName(new_pet[0]);
                            pet.setAge(Integer.parseInt(new_pet[1]));
                            return  pet;
                        }

                        return null;
                    }
                });
            }
        };
    }
}