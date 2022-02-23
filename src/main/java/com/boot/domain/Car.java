/*
 * Copyright 2021 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.boot.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @title 阳光正好，微风不燥
 * @data 2021/12/3
 */
@ConfigurationProperties(prefix = "mycar")// prefix ="" 表示配置文件中的前缀
public class Car {
    private String brand;
    private String price;
    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
    public Car() {
    }
    public Car(String brand, String price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand() {return brand;}
    public void setBrand(String brand) { this.brand = brand;}

    public String getPrice() {return price;}
    public void setPrice(String price) {this.price = price;}
}