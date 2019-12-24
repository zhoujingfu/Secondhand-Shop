package com.gjj.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Goods;
import com.gjj.services.GoodsService;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by gjj on 2018-03-11.
 */
public class ObjectMapperBuilder {

    public Map<String,Object> mapObjects (Object object,ObjectMapper objectMapper) {
        Map<String,Object> map = new HashMap();
        try {

            String aa =  object.toString();
            map = objectMapper.readValue( object.toString(),Map.class);
//            map = objectMapper.writeValueAsString(object);

        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Page<Map<String,Object>> mapPagedObjects (Page<?> objects,ObjectMapper objectMapper) {
        List<Map<String, Object>> mappedObjects = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap();
            for (Object object : objects.getContent()) {
                map = objectMapper.readValue(objectMapper.writeValueAsString(object),Map.class);
                mappedObjects.add(map);
            }
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageImpl<>(mappedObjects, new PageRequest(objects.getNumber(),
                objects.getSize(), objects.getSort()), objects.getTotalElements());
    }



    public ObjectMapper build(Method method) throws CannotCompileException {
        IgnoreProperty ignoreProperty = method.getAnnotation(IgnoreProperty.class);
        String[] value = ignoreProperty.value();
        Class<?> pojo = ignoreProperty.pojo();
        checkParamter(method,value,pojo);
        Class<?> clazz=doBuild(value);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.addMixIn(pojo, clazz);
        return objectMapper;
    }
    /**
     * 根据传入的参数构造一个class
     * @throws CannotCompileException
     */
    public Class<?> doBuild(String[] values) throws CannotCompileException{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeInterface("ProxyMixInAnnotation" + System.currentTimeMillis());
        ClassFile classFile = cc.getClassFile();
        ConstPool cp = classFile.getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(cp,
                AnnotationsAttribute.visibleTag);
        Annotation jsonIgnorePropertiesAnnotation = new Annotation(
                JsonIgnoreProperties.class.getName(), cp);
        BooleanMemberValue ignoreUnknownMemberValue = new BooleanMemberValue(false, cp);
        //
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(cp);
        Collection<MemberValue> memberValues = new HashSet<MemberValue>();
        for(int i=0;i<values.length;i++){
            StringMemberValue memberValue = new StringMemberValue(cp);// 将name值设入注解内
            memberValue.setValue(values[i]);
            memberValues.add(memberValue);
        }
        arrayMemberValue.setValue(memberValues.toArray(new MemberValue[]{}));
        jsonIgnorePropertiesAnnotation.addMemberValue("value", arrayMemberValue);
        jsonIgnorePropertiesAnnotation.addMemberValue("ignoreUnknown", ignoreUnknownMemberValue);
        attr.addAnnotation(jsonIgnorePropertiesAnnotation);
        classFile.addAttribute(attr);
        Class clazz = cc.toClass();
        return clazz;
    }
    protected void checkParamter(Object... objs){
        boolean isTrue=true;
        if(objs==null||objs.length<=0){
            isTrue=false;
        }
        for(Object obj:objs){
            if(obj==null){
                isTrue=false;
            }
        }
        if(!isTrue){
            throw new RuntimeException("参数出现错误");
        }
    }
}
