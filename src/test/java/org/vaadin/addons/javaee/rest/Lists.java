package org.vaadin.addons.javaee.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lists<VALUE> {

    private List<VALUE> values = new ArrayList<VALUE>();

    public Lists() {
    }

    public Lists(List<VALUE> values) {
        this.setValues(values);
    }

    @XmlElement(name = "collection")
    public List<VALUE> getValues() {
        return values;
    }

    public void setValues(List<VALUE> values) {
        this.values = values;
    }

}