/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.vaadin.addons.javaee.events;

import java.util.HashMap;
import java.util.Map;

public class NavigationEvent {

    private String pageName;

    private Map<String, Object> parameters = new HashMap<String, Object>();

    public NavigationEvent(String pageName) {
        this.pageName = pageName;
    }

    public NavigationEvent(String pageName, Map<String, Object> parameters) {
        this.pageName = pageName;
        this.parameters = parameters;
    }

    public String getPageName() {
        return pageName;
    }

    public Object getParam(String key) {
        return parameters.get(key);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String key, Object value) {
        parameters.put(key, value);
    }

}
