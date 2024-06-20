package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class Country {
   // country Code

   // country Name
   public String name;
   public String startYear;
   public String endYear;
   public String loss_percentage;
   // public String year;
   public String lossStartYear;
   public String loss_end_year;
   public String loss_difference;
   public String activity;
   public String food_supply_stage;
   public String cause_of_loss;
   /**
    * Create a Country and set the fields
    */
   public Country(String name, String startYear, String endYear) {
      this.name = name;
      this.startYear = startYear;
      this.endYear = endYear;

   }

   public Country() {
    //TODO Auto-generated constructor stub
}

// public String getM49Code() {
//       return m49Code;
//    }

   

   // public String getStartYear() {
   //    return startYear;
   // }

   // public String getEndYear() {
   //    return endYear;
   // }
}
