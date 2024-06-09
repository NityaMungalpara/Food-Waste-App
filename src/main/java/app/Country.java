package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class Country {
   // country Code
   private String m49Code;

   // country Name
   private String name;
   private String startYear;
   private String endYear;

   public String countryName;

public String year;

public String loss_percentage;

   /**
    * Create a Country and set the fields
    */
   public Country(String m49Code, String name, String startYear, String endYear) {
      this.m49Code = m49Code;
      this.name = name;
      this.startYear = startYear;
      this.endYear = endYear;
   }

   public Country() {
    //TODO Auto-generated constructor stub
}

public String getM49Code() {
      return m49Code;
   }

   public String getName() {
      return name;
   }

   public String getStartYear() {
      return startYear;
   }

   public String getEndYear() {
      return endYear;
   }
}
