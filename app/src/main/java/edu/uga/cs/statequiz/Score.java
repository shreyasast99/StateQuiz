package edu.uga.cs.statequiz;


/**
 * This class (a POJO) represents a single job lead, including the id, company name,
 * phone number, URL, and some comments.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Score {

    private long   id;
    private String date;
    private int score;

    public Score()
    {
        this.id = -1;
        this.date = null;
        this.score = 0;
    }

    public Score( String date, int score ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.date = date;
        this.score = score;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getScore() { return score; }

    public void setScore(int score)
    {
        this.score = score;
    }




    public String toString()
    {
        return id + ": " + date + " " + score + " ";
    }
}