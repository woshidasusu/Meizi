package com.dasu.ganhuo.mode.logic.history;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dasu on 2017/4/29.
 */

public class SomedayEntity implements Serializable{

    private String title;
    private List<Type> types;

    public static class Type implements Serializable {
        private String text;
        private String href;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
