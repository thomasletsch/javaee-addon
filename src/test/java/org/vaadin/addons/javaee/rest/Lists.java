package org.vaadin.addons.javaee.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lists<VALUE> {

    private List<VALUE> values = new ArrayList<VALUE>();

    public Lists(List<VALUE> values) {
        this.values = values;
    }

    @XmlAnyElement(lax = true)
    public List<VALUE> getValues() {
        return values;
    }

}