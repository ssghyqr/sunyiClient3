package common;

import java.util.List;

public class MessageGroup extends Message{
    private List<String> getterList;

    public List<String> getGetterList() {
        return getterList;
    }

    public void setGetterList(List<String> getterList) {
        this.getterList = getterList;
    }
}
