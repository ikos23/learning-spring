package com.ivk23.helloworld.newway.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

public class SimpleBFPP implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory фабрикаБобів) throws BeansException {
        for (String імяЩеНеБоба : фабрикаБобів.getBeanDefinitionNames()) {
            if (імяЩеНеБоба.equals("simpleBean")) {
                final var майжеБіб = фабрикаБобів.getBean(імяЩеНеБоба);
                final var field = ReflectionUtils.findField(SimpleBean.class, "field");
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, майжеБіб, "Field Value set by BeanFactoryPostProcessor");
            }
        }
    }

}
