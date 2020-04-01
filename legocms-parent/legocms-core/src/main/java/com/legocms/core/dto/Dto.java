package com.legocms.core.dto;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.legocms.core.common.ToStringStyle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dto implements Serializable {
    private static final long serialVersionUID = 8478873767622245823L;

    public String toJson() {
        String json = JSON.toJSONString(this);
        log.debug("生成JSON数据：" + json);
        return json;
    }

    /**
     * dto转为xml字符串
     */
    public String toXml() throws Exception {
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); // 编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // 是否省略xml头信息
        StringWriter writer = new StringWriter();
        marshaller.marshal(this.getClass(), writer);
        return writer.toString();
    }

    /**
     * xml字符串转为dto对象
     */
    @SuppressWarnings("unchecked")
    public <T extends Dto> T toDto(String xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xml));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(xsr);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.INSTANCE).toString();
    }
}
