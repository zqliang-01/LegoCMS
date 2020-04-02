package com.legocms.core.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.legocms.core.exception.CoreException;

/**
  * 序列话工具
 *
 */
public class SerializerUtil {

    private static final Logger logger = LoggerFactory.getLogger(SerializerUtil.class);

    /**
         * 序列化对象
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new CoreException("序列化对象(" + obj + ")为空!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        }
        catch (Exception e) {
            throw new CoreException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
        }
        finally {
            buffer.clear();
        }
    }

    /**
         * 反序列化对象
     */
    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        try {
            T instance = targetClass.newInstance();
            Schema<T> schema = RuntimeSchema.getSchema(targetClass);
            ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
            return instance;
        }
        catch (Exception e) {
            throw new CoreException("反序列化过程中依据类型创建对象失败!", e);
        }
    }

    /**
         * 序列化列表
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serializeList(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            throw new CoreException("序列化对象列表(" + objList + ")参数异常!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
            return bos.toByteArray();
        }
        catch (Exception e) {
            throw new CoreException("序列化对象列表(" + objList + ")发生异常!", e);
        }
        finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            }
            catch (IOException e) {
                logger.error("关闭序列化输入流失败", e);
            }
        }
    }

    /**
         * 反序列化列表
     */
    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new CoreException("反序列化对象发生异常,byte序列为空!");
        }

        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        try {
            return ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        }
        catch (IOException e) {
            throw new CoreException("反序列化对象列表发生异常!", e);
        }
    }

}
