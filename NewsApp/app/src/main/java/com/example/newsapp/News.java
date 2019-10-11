package com.example.newsapp;

public class News {

    private String Web_Title ;
    private String Section_Name ;
    private String Date_Published ;
    private String Web_Url ;
    private String Author_Name ;

    public News (String Web_Title , String Section_name , String Date_Published , String Web_Url , String Author_Name )
    {
        this.Web_Title = Web_Title ;
        this.Section_Name =Section_name ;
        this.Date_Published = Date_Published ;
        this.Web_Url = Web_Url ;
        this.Author_Name = Author_Name ;
    }

    public String getWeb_Title() {
        return Web_Title;
    }

    public String getSection_Name() {
        return Section_Name;
    }

    public String getDate_Published() {
        return Date_Published;
    }

    public String getWeb_Url() {
        return Web_Url;
    }

    public String getAuthor_Name() {
        return Author_Name;
    }
}
