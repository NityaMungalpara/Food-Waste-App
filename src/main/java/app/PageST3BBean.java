package app;

public class PageST3BBean {
    public String name;
    public String avgLossPer;
    public String avgWastePer;
    public String ratio;
    public String avgPer;
    public String maxPer;
    public String errorMessage;


    // Default constructor
    public PageST3BBean() {
    }

    // Constructor with all parameters
    public PageST3BBean(String name, String avgLossPer, String avgWastePer, String ratio, String avgPer, String maxPer) {
        this.name = name;
        this.avgLossPer = avgLossPer;
        this.avgWastePer = avgWastePer;
        this.ratio = ratio;
        this.avgPer = avgPer;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvgLossPer() {
        return avgLossPer;
    }

    public void setAvgLossPer(String avgLossPer) {
        this.avgLossPer = avgLossPer;
    }

    public String getAvgWastePer() {
        return avgWastePer;
    }

    public void setAvgWastePer(String avgWastePer) {
        this.avgWastePer = avgWastePer;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getAvgPer() {
        return avgPer;
    }
    public void setAvgPer(String avgPer) {
        this.avgPer = avgPer;
    }
    public String getMaxPer() {
        return maxPer;
    }
    public void setMaxPer(String maxPer) {
        this.maxPer = maxPer;
    }

// error alert

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static void add(PageST3BBean errorBean) {

        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }
}
