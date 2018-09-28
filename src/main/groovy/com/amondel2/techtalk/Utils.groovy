package com.amondel2.techtalk
import java.util.UUID
import groovy.transform.CompileStatic

@CompileStatic
class Utils{

    String idGenerator(){
        UUID.randomUUID().toString().replaceAll("-", "");
    }

}