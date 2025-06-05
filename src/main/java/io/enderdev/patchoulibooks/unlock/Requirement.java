package io.enderdev.patchoulibooks.unlock;

public class Requirement {
    private String type;
    private String trigger;

    public String getType() {
        return type;
    }

    public String getTrigger() {
        return trigger;
    }

    public boolean unlocked() {
        return false;
    }
}
