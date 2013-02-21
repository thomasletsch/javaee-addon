package org.vaadin.addons.javaee.selenium.ordered;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OrderedRunner extends BlockJUnit4ClassRunner {

    public OrderedRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> list = super.computeTestMethods();
        List<FrameworkMethod> copy = new ArrayList<>(list);
        Collections.sort(copy, new Comparator<FrameworkMethod>() {

            @Override
            public int compare(FrameworkMethod o1, FrameworkMethod o2) {
                Order o1order = o1.getAnnotation(Order.class);
                Order o2order = o2.getAnnotation(Order.class);
                if (o1order == null || o2order == null) {
                    return o2.getName().compareTo(o1.getName());
                }
                return o1order.value() - o2order.value();
            }
        });
        return copy;
    }
}