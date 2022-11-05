package domain;

public class GroupDTO {
    private String groupName;
    private String groupDesc;
    private Long groupId;
    private Long chattingRecordGroupId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getChattingRecordGroupId() {
        return chattingRecordGroupId;
    }

    public void setChattingRecordGroupId(Long chattingRecordGroupId) {
        this.chattingRecordGroupId = chattingRecordGroupId;
    }
}
