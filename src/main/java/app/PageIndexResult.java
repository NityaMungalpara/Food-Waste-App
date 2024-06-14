package app;

public class PageIndexResult {
    public String year;
    public String name;
    public String avgMaxLossPercentage;
    public String errorMessage;

    public PageIndexResult(){
    }

    public PageIndexResult(String year, String name, String avgMaxLossPercentage) {
        this.year = year;
        this.name = name;
        this.avgMaxLossPercentage = avgMaxLossPercentage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvgMaxLossPercentage() {
        return avgMaxLossPercentage;
    }

    public void setAvgMaxLossPercentage(String avgMaxLossPercentage) {
        this.avgMaxLossPercentage = avgMaxLossPercentage;
    }

    public void setErrorMessage(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'setErrorMessage'");
    }
    public String getErrorMessage() {
        return errorMessage;
    }


}
