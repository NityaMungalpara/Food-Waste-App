package app;

public class PageST2BBean {
    public String groupName;
    public String startYearAvg;
    public String endYearAvg;
    public String lossDifference;
    public String activity;
    public String foodSupplyStage;
    public String causeOfLoss;
    // public String mostFrequentFilter;
    public String errorMessage;


    // Default constructor
    public PageST2BBean() {
    }

    // Constructor with all parameters
    public PageST2BBean(String groupName, String startYearAvg, String endYearAvg, String lossDifference, String activity, String foodSupplyStage, String causeOfLoss) {
        this.groupName = groupName;
        this.startYearAvg = startYearAvg;
        this.endYearAvg = endYearAvg;
        this.lossDifference = lossDifference;
        this.activity = activity;
        this.foodSupplyStage = foodSupplyStage;
        this.causeOfLoss = causeOfLoss;
        // this.mostFrequentFilter = mostFrequentFilter;
    }

    // Getters and Setters
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStartYearAvg() {
        return startYearAvg;
    }

    public void setStartYearAvg(String startYearAvg) {
        this.startYearAvg = startYearAvg;
    }

    public String getEndYearAvg() {
        return endYearAvg;
    }

    public void setEndYearAvg(String endYearAvg) {
        this.endYearAvg = endYearAvg;
    }

    public String getLossDifference() {
        return lossDifference;
    }

    public void setLossDifference(String lossDifference) {
        this.lossDifference = lossDifference;
    }

    // public String getMostFrequentFilter() {
    //     return mostFrequentFilter;
    // }

    // public void setMostFrequentFilter(String mostFrequentFilter) {
    //     this.mostFrequentFilter = mostFrequentFilter;
    // }

    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getFoodSupplyStage() {
        return foodSupplyStage;
    }

    public void setFoodSupplyStage(String foodSupplyStage) {
        this.foodSupplyStage = foodSupplyStage;
    }

    public String getCauseOfLoss() {
        return causeOfLoss;
    }

    public void setCauseOfLoss(String causeOfLoss) {
        this.causeOfLoss = causeOfLoss;
    }

// error alert

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static void add(PageST2BBean errorBean) {

        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }
}


