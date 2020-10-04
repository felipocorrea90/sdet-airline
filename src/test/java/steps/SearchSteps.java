package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.Search;
import utils.Driver;

import java.text.ParseException;
import java.util.List;

public class SearchSteps {
    // Get the Driver
    static WebDriver wd = Driver.getDriver();

    // Instantiate POM class
    private Search search = new Search(wd);

    @Given("user visits the Ryanair website")
    public void user_visits_the_ryanair_website() {
        wd.get("https://www.ryanair.com/gb/en");
    }

    @When("user search for a trip from {string} to {string} using the following criteria")
    public void user_search_for_a_trip_with_given_criteria(String fromCountry, String toCountry, List<String> flightInfo) {
        search.searchRoundTripFlight(wd, fromCountry, toCountry, flightInfo);
    }

    @When("user change the departure date to {string} and return date to {string}")
    public void user_change_the_departure_date_to_and_return_date_to(String departureDate, String returnDate) throws ParseException, InterruptedException {
        search.updateDates(wd, departureDate, returnDate);
    }

    @When("user selects value fare")
    public void select_value_fare() throws InterruptedException {
        search.selectValueFare(wd);
    }

    @When("user adds the following passengers names")
    public void user_adds_the_following_passengers_names(io.cucumber.datatable.DataTable dataTable) {
        search.addPassengers(wd);
    }

    /*
    @When("select the small package option")
    public void select_the_small_package_option() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("select the seats")
    public void select_the_seats() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("clikt the continue to payment option")
    public void clikt_the_continue_to_payment_option() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }*/

    @Then("seat selection view should be displayed")
    public void validate_seat_view() {
        Assert.assertTrue(search.seatsViewDisplayed(wd));
    }

}
