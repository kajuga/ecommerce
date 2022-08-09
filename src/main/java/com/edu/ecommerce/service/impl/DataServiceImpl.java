package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.service.interfaces.DataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public Object checkData(Object obj, String fieldPath) {
        var list = Arrays.asList(fieldPath.split("/"));
        var fieldReference = obj;
        var lastElement = list.get(list.size() - 1);

        for (String fieldName : list) {
            fieldReference = invokeGetter(fieldReference, fieldName);
            if (Objects.isNull(fieldReference)) {
                return null;
            } else if (fieldName.equals(lastElement)) {
                return fieldReference;
            }
        }
        return null;
    }

    @Override
    public Object invokeGetter(Object obj, String fieldName) {
        try {
            var pd = new PropertyDescriptor(fieldName, obj.getClass());
            var getter = pd.getReadMethod();
            return getter.invoke(obj);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean isObjectInListByField(List<?> list, String fieldName, String value) {
        List<?> requiredList = list.stream()
                .map(newTraineeDto ->
                        invokeGetter(newTraineeDto, fieldName))
                .collect(Collectors.toList());
        return requiredList.contains(value);
    }

    @Override
    public void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            } else if (srcValue instanceof Collection) {
                objectToList(emptyNames, pd, (Collection<?>) srcValue);
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private void objectToList(Set<String> emptyNames, PropertyDescriptor pd, Collection<?> srcValue) {
        List<?> list = new ArrayList<>(srcValue);
        if(list.isEmpty()){
            emptyNames.add(pd.getName());
        }
    }
}
