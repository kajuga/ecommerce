package com.edu.userservice.service.interfaces;

import java.util.List;

public interface DataService {
    Object checkData(Object obj, String fieldPath);
    Object invokeGetter(Object obj, String fieldName);

    Boolean isObjectInListByField(List<?> list, String field, String value);

    void copyNonNullProperties(Object src, Object target);
}
