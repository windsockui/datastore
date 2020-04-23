package com.windsockui.datastore.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsData {

    LinkedList<Component> components;

    public LinkedList<Component> getComponents() {
        return components;
    }

    public void setComponents(LinkedList<Component> components) {
        this.components = components;
    }

    public static class Component {
        String id;
        String componentName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComponentName() {
            return componentName;
        }

        public void setComponentName(String componentName) {
            this.componentName = componentName;
        }
    }

}

