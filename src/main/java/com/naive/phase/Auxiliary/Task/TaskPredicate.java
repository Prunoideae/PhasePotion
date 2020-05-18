package com.naive.phase.Auxiliary.Task;

import java.util.ArrayList;

public class TaskPredicate extends TaskElement {

    private boolean valid = true;

    public TaskPredicate(Object element) {
        super(element);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean ignite(TaskElement elements) {
        if (elements.elementType != TaskElement.Type.COMPOSITE) {
            throw new IllegalArgumentException("Not a composite element!");
        }
        boolean canStart = true;
        for (TaskElement sub : subElements) {
            boolean matched = false;
            for (TaskElement have : elements.getSubElements()) {
                if (sub.match(have.unwrap())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                canStart = false;
                break;
            }

        }
        return canStart && valid;
    }
}
