package ibf2021.stockapp.server.repositories;

public interface SQLs {
    public static final String SQL_ADD_PORFOLIOITEM = "insert into portfolio(username,ticker,date_added) values (?,?,?)";
       // "insert into portfolio(username,ticker,date_added) values ("brian", "aapl", "2014-03-14");"
    public static final String SQL_CHECK_USERNAME_EXIST = "select count(*) username_valid from user where username = ?";
       //select count(*) user_count from user where username = "brian";
       public static final String SQL_CHECK_PASSWORD_OK = "select count(*) password_valid from user where password = ?";
       //select count(*) user_count from user where password = "root";
       public static final String SQL_GET_USERNAME_RECORD = "select * from user where username = ?";
       //select count(*) user_count from user where password = "root";
       public static final String SQL_GET_PORTFOLIO_BY_USERNAME = "select * from portfolio where username = ?";
       //select count(*) user_count from user where password = "root";
       public static final String SQL_REGISTER_USER = "insert into user(username,password,email) values (?,SHA2(?,256),?)";
       // "insert into portfolio(username,ticker,date_added) values ("brian", "aapl", "2014-03-14");"
       public static final String SQL_DELETE_FROM_PORTFOLIO = "delete from portfolio where username = ? and ticker = ?";
       //select count(*) user_count from user where password = "root";
}
// select * from portfolio;
//  describe user;
//  insert into user (username,password,email) values ("brian",SHA2("root",256),"email");
//  insert into portfolio(username,ticker,date_added) values ("brian", "aapl", "2014-03-14");
//  select * from portfolio;
//  select * from user;

//  select count(*) user_count from user where username = "brian";
//  select count(*) password_valid from user where password = "root";
//  delete from user where username = "brian4";
//  select * from portfolio where username = "brian";
//delete from portfolio where username ="brian" and ticker = "aapl";