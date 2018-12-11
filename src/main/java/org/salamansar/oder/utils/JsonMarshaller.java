package org.salamansar.oder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Salamansar
 */
@Slf4j
public class JsonMarshaller {//todo: add unit test
    private ObjectMapper mapper;

    public JsonMarshaller(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    public String toJsonString(Object obj) {
        try {
            return mapper.writer().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            String msg = "Failure to marshall object to Json";
            log.error(msg, ex);
            return msg;
        }
    }
    
}
