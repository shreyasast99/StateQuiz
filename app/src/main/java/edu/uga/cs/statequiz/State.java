package edu.uga.cs.statequiz;


/**
 * This class (a POJO) represents a single job lead, including the id, company name,
 * phone number, URL, and some comments.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class State {

    private long   id;
    private String state;
    private String capital;
    private String secondCity;
    private String thirdCity;

    public State()
    {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.secondCity = null;
        this.thirdCity = null;
    }

    public State( String state, String capital, String secondCity, String thirdCity ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.state = state;
        this.capital = capital;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getSecondCity()
    {
        return secondCity;
    }

    public void setSecondCity(String secondCity)
    {
        this.secondCity=secondCity;
    }

    public String getThirdCity()
    {
        return thirdCity;
    }

    public void setThirdCity(String thirdCity)
    {
        this.thirdCity=thirdCity;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital=capital;
    }



    public String toString()
    {
        return id + ": " + state + " " + capital + " " + secondCity + " " + thirdCity;
    }
}
