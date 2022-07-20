package Project.Models.Notifications;


import Project.Enums.ResourceEnum;

public class Trade extends Notification{
    String type;
    ResourceEnum  suggestResources;
    Integer suggestGold;
    ResourceEnum requiredResources;
    Integer requiredGold;
    String hostCivName;
    String guestCivName;

    public Trade(ResourceEnum suggestResources, Integer suggestGold, ResourceEnum requiredResources, Integer requiredGold, String hostCivName, String guestCivName, String name) {
        this.suggestResources = suggestResources;
        this.suggestGold = suggestGold;
        this.requiredResources = requiredResources;
        this.requiredGold = requiredGold;
        this.hostCivName = hostCivName;
        this.guestCivName = guestCivName;
        this.message = initializeMessage();
        super.name = name;
        this.type = this.getClass().getName();
    }
    private String initializeMessage(){
        if(this.suggestResources == null){
                return "host civ : " + hostCivName + " Suggest : Gold " + suggestGold + " Requires : Resource " + requiredResources;
        } else {
            if(requiredResources == null){
                return "host civ : " + hostCivName + " Suggest : Resource " + suggestResources +  " Requires : Gold " + requiredGold;
            } else {
                return "host civ : " + hostCivName + " Suggest : Resource " + suggestResources + " Requires : Resource " + requiredResources;
            }
        }
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResourceEnum getSuggestResources() {
        return suggestResources;
    }

    public void setSuggestResources(ResourceEnum suggestResources) {
        this.suggestResources = suggestResources;
    }

    public Integer getSuggestGold() {
        return suggestGold;
    }

    public void setSuggestGold(Integer suggestGold) {
        this.suggestGold = suggestGold;
    }

    public ResourceEnum getRequiredResources() {
        return requiredResources;
    }

    public void setRequiredResources(ResourceEnum requiredResources) {
        this.requiredResources = requiredResources;
    }

    public Integer getRequiredGold() {
        return requiredGold;
    }

    public void setRequiredGold(Integer requiredGold) {
        this.requiredGold = requiredGold;
    }

    public String getHostCivName() {
        return hostCivName;
    }

    public void setHostCivName(String hostCivName) {
        this.hostCivName = hostCivName;
    }

    public String getGuestCivName() {
        return guestCivName;
    }

    public void setGuestCivName(String guestCivName) {
        this.guestCivName = guestCivName;
    }

    public String getName() {
        return super.getName();
    }
}
