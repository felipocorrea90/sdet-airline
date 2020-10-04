package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Search {

    public Search(WebDriver wd) {
        PageFactory.initElements(wd, this);
    }

    @FindBy(id = "input-button__departure")
    WebElement departureField;

    @FindBy(id = "input-button__destination")
    WebElement destinationField;

    @FindBy(xpath = "//fsw-input-button[@uniqueid='dates-from']")
    WebElement departField;

    @FindBy(xpath = "//fsw-input-button[@uniqueid='passengers']")
    WebElement passengersField;

    @FindBy(xpath = "//button[@data-ref='flight-search-widget__cta']")
    WebElement searchButton;

    @FindBy(xpath = "//ry-checkbox[@data-ref='terms-of-use__terms-checkbox']")
    WebElement termsOfUse;

    @FindBy(xpath = "//ry-counter[@data-ref='passengers-picker__adults']//ry-counter-button[@data-ref='counter.counter__increment']/parent::div")
    WebElement addAdultButton;

    @FindBy(xpath = "//ry-counter[@data-ref='passengers-picker__children']//ry-counter-button[@data-ref='counter.counter__increment']/parent::div")
    WebElement addChildrenButton;

    public void searchRoundTripFlight(WebDriver wd, String fromCountry, String toCountry, List<String> flightInfo) {
        departureField.click();
        selectAirport(wd, fromCountry, flightInfo.get(0));
        destinationField.click();
        selectAirport(wd, toCountry, flightInfo.get(1));
        WebDriverWait wait = new WebDriverWait(wd, 10);
        new WebDriverWait(wd, 2).until(ExpectedConditions.visibilityOf(departField));
        departField.click();
        selectDate(wd, flightInfo.get(2));
        selectDate(wd, flightInfo.get(3));
        passengersField.click();
        addAdults(Integer.valueOf(flightInfo.get(4)));
        addChildrens(Integer.valueOf(flightInfo.get(5)));
        searchButton.click();
    }

    public void selectAirport(WebDriver wd, String country, String airport) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                wd.findElement(By.xpath("//span[@data-ref='country__name' and contains(.,'" + country + "')]")).click();
                wd.findElement(By.xpath("//span[@data-ref='airport-item__name' and contains(.,'" + airport + "')]")).click();
                break;
            } catch (StaleElementReferenceException e) {

            }
            attempts++;
        }
    }

    public void selectDate(WebDriver wd, String date) {
        String[] splitedDate = date.split("\\s+");
        int attempts = 0;
        while (attempts < 2) {
            try {
                wd.findElement(By.xpath("//div[@data-ref='m-toggle-months-item' and contains(.,'" + splitedDate[0] + "')][1]")).click();
                wd.findElement(By.xpath("//div[@data-type='day' and @data-value='" + splitedDate[1] + "'][1]")).click();
                break;
            } catch (StaleElementReferenceException e) {

            }
            attempts++;
        }
    }


    public void addAdults(int adults) {
        if (adults > 1) {
            for (int i = 1; i < adults; i++) {
                addAdultButton.click();
            }
        }
    }

    public void addChildrens(int childrens) {
        if (childrens > 0) {
            for (int i = 0; i < childrens; i++) {
                addChildrenButton.click();
            }
        }
    }

    public void updateDates(WebDriver wd, String departureDate, String returnDate) throws ParseException, InterruptedException {
        updateDepart(wd, departureDate);
        updateReturn(wd, returnDate);

    }

    public void updateDepart(WebDriver wd, String departureDate) throws ParseException, InterruptedException {
        String[] splitedDate = departureDate.split("\\s+");

        // Close cookies
        wd.findElement(By.xpath("//div[@class='cookie-popup__close']")).click();

        // Get current date
        int currentDay = Integer.valueOf(wd.findElement(By.xpath("//carousel-item[@class='ng-star-inserted']//button[@data-selected='true']//span[@class='date-item__day-of-month h4 date-item__day-of-month--selected']")).getText());
        String currentMonth = wd.findElement(By.xpath("//carousel-item[@class='ng-star-inserted']//button[@data-selected='true']//span[@class='date-item__month h4 date-item__month--selected']")).getText();
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String dateInput1 =  currentDay + " " + getMonth(currentMonth) + " " + year;

        // Get updated date
        String updatedDay = splitedDate[1];
        String updatedMonth = getMonth(splitedDate[0]);
        String dateInput2 =  updatedDay + " " + updatedMonth + " " + year;

        // Get dates difference
        Date date1 = myFormat.parse(dateInput1);
        Date date2 = myFormat.parse(dateInput2);
        long diff = date2.getTime() - date1.getTime();
        int diffDays = Integer.valueOf(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
        int clicks = diffDays / 5;

        // Click scroll pages
        if (clicks < 0) {
            for (int i = 0; i < Math.abs(clicks) ; i++) {
                wd.findElement(By.xpath("//button[@class='carousel-prev']")).click();
                Thread.sleep(2000);
            }
        } else {
            for (int i = 0; i < clicks ; i++) {
                wd.findElement(By.xpath("//button[@class='carousel-next']")).click();
                Thread.sleep(2000);
            }
        }

        // Select new date
        wd.findElement(By.xpath("//span[@class='date-item__day-of-month b2' and contains(.,'" + updatedDay + "')]/ancestor::button")).click();

    }

    public void updateReturn(WebDriver wd, String returnDate) throws ParseException, InterruptedException {
        String[] splitedDate = returnDate.split("\\s+");

        // Get current date
        int currentDay = Integer.valueOf(wd.findElement(By.xpath("(//carousel-item[@class='ng-star-inserted']//button[@data-selected='true']//span[@class='date-item__day-of-month h4 date-item__day-of-month--selected'])[2]")).getText());
        String currentMonth = wd.findElement(By.xpath("(//carousel-item[@class='ng-star-inserted']//button[@data-selected='true']//span[@class='date-item__month h4 date-item__month--selected'])[2]")).getText();
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String dateInput1 =  currentDay + " " + getMonth(currentMonth) + " " + 2021;

        // Get updated date
        String updatedDay = splitedDate[1];
        String updatedMonth = getMonth(splitedDate[0]);
        String dateInput2 =  updatedDay + " " + updatedMonth + " " + year;

        // Get dates difference
        Date date1 = myFormat.parse(dateInput1);
        Date date2 = myFormat.parse(dateInput2);
        long diff = date2.getTime() - date1.getTime();
        int diffDays = Integer.valueOf(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
        int clicks = diffDays / 5;

        // Click scroll pages
        if (clicks < 0) {
            for (int i = 0; i < Math.abs(clicks) ; i++) {
                wd.findElement(By.xpath("(//button[@class='carousel-prev'])[2]")).click();
                Thread.sleep(2000);
            }
        } else {
            for (int i = 0; i < clicks ; i++) {
                wd.findElement(By.xpath("(//button[@class='carousel-next'])[2]")).click();
                Thread.sleep(2000);
            }
        }

        // Select new date
        wd.findElement(By.xpath("//span[@class='date-item__day-of-month b2' and contains(.,'" + updatedDay + "')]/ancestor::button")).click();

    }

    public String getMonth(String month) {
        switch (month) {
            case "Dec":
                return "12";
            case "Jan":
                return "01";
            default:
                return "no month match";
        }
    }

    public void selectValueFare(WebDriver wd) throws InterruptedException {
        Thread.sleep(2000);
        wd.findElement(By.xpath("//flight-card")).click();
        Thread.sleep(2000);
        wd.findElement(By.xpath("//button[@class='fare-card__button fare-card__price ry-button--outline-dark-blue']")).click();
        Thread.sleep(2000);
        wd.findElement(By.xpath("(//flight-card)[2]")).click();
        Thread.sleep(2000);
        wd.findElement(By.xpath("//button[@class='fare-card__button fare-card__price ry-button--outline-dark-blue']")).click();
    }

    public void addPassengers(WebDriver wd) {
        wd.findElement(By.xpath("//button[@class='login-touchpoint__expansion-bar']")).click();
        wd.findElement(By.xpath("(//ry-dropdown)[1]")).click();
        wd.findElement(By.xpath("//ry-dropdown-item[@data-ref='title-item-2']")).click();
        wd.findElement(By.xpath("//input[@id='formState.passengers.ADT-0.name']")).sendKeys("Sónia");
        wd.findElement(By.xpath("//input[@id='formState.passengers.ADT-0.surname']")).sendKeys("Pereira");
        wd.findElement(By.xpath("(//ry-dropdown)[2]")).click();
        wd.findElement(By.xpath("//ry-dropdown-item[@data-ref='title-item-0']")).click();
        wd.findElement(By.xpath("//input[@id='formState.passengers.ADT-1.name']")).sendKeys("Diogo");
        wd.findElement(By.xpath("//input[@id='formState.passengers.ADT-1.surname']")).sendKeys("Bettencourt");
        wd.findElement(By.xpath("//input[@id='formState.passengers.CHD-0.name']")).sendKeys("Inês");
        wd.findElement(By.xpath("//input[@id='formState.passengers.CHD-0.surname']")).sendKeys("Marçal");
        wd.findElement(By.xpath("//button[@class='continue-flow__button ry-button--gradient-yellow']")).click();
    }

    public boolean seatsViewDisplayed(WebDriver wd) {
        if (wd.findElement(By.xpath("//seat-map")) != null) {
            return true;
        } else {
            return false;
        }
    }

}
