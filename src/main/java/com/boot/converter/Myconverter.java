package com.boot.converter;

import com.boot.domain.Person;
import lombok.val;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Myconverter implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Person.class);
    }

    /**
     * 获取所有支持的 媒体类型 MeadiaType
     * <p>
     * 我们需要添加自定义的媒体类型[application/x-lin]
     *
     * @return
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes("application/x-lin");
    }

    @Override
    public Person read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }


    @Override
    public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //自定义协议 数据的写出
        String data = person.getUserName() + ";" + person.getAge() + ";" + person.getBirth() + ";" + person.getPet().getName() + "," + person.getPet().getAge();
        OutputStream outputStream = outputMessage.getBody();
        outputStream.write(data.getBytes());
    }
}
