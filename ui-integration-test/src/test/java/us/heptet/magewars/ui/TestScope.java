package us.heptet.magewars.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jade on 15/09/2016.
 */
public class TestScope<T extends Serializable> implements Scope {
    private static Logger logger = LoggerFactory.getLogger(TestScope.class);
    ConcurrentHashMap<T, Map<String, Object>> scopeMap = new ConcurrentHashMap<T, Map<String, Object>>();
    T currentScope;

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        logger.debug("get {} {}", currentScope, name);
        scopeMap.putIfAbsent(currentScope, new ConcurrentHashMap<>());
        Map<String, Object> objectMap = scopeMap.get(currentScope);
        if(objectMap.containsKey(name)) {
            return objectMap.get(name);
        }
        Object value = objectFactory.getObject();
        objectMap.put(name, value);
        return value;
    }

    @Override
    public Object remove(String name) {
        return scopeMap.get(currentScope).remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return currentScope.toString();
    }

    public T getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(T currentScope) {
        this.currentScope = currentScope;
    }
}
