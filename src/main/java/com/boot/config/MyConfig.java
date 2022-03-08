/*
 * Copyright 2021 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.boot.config;

import com.boot.converter.Myconverter;
import com.boot.domain.Car;
import com.boot.domain.Pet;
import lombok.val;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title 阳光正好，微风不燥
 * @data 2021/11/26
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Car.class)
public class MyConfig {
    //1.WebMvcConfigurer 定制化springMVC的功能
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
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
                        if (source.length() != 0 && !"".equals(source)) {
                            String[] new_pet = source.split(",");
                            Pet pet = new Pet();
                            pet.setName(new_pet[0]);
                            pet.setAge(Integer.parseInt(new_pet[1]));
                            return pet;
                        }

                        return null;
                    }
                });
            }

            //拓展自定义的MessageConverter
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                WebMvcConfigurer.super.extendMessageConverters(converters);
                converters.add(new Myconverter());
            }

            //配置自定义 内容协商

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                WebMvcConfigurer.super.configureContentNegotiation(configurer);
                Map<String, MediaType> map = new HashMap<>();
                map.put("lin", MediaType.parseMediaType("application/x-lin"));
                ParameterContentNegotiationStrategy strategy = new ParameterContentNegotiationStrategy(map);
                configurer.strategies(Arrays.asList(strategy));
            }
        };
    }
}