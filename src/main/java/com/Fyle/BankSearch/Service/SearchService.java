package com.Fyle.BankSearch.Service;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Fyle.BankSearch.Beans.Bank;
import com.Fyle.BankSearch.Connection.DBConnect;

@RestController
public class SearchService {

    public Map<String, List> autocompleteService(@RequestParam Map<String, String> customQuery) {

        String q = "", limit = "", offset = "";

        List<Bank> banks = new ArrayList<>();
        if (customQuery.containsKey("q"))
            q = customQuery.get("q");
        q = q.toLowerCase();
        String query = "Select * from branches where LOWER(branch) like '%" + q + "%' order by ifsc";
        if (customQuery.containsKey("limit")) {
            limit = customQuery.get("limit");
            query += " LIMIT " + limit;
        }
        if (customQuery.containsKey("offset")) {
            offset = customQuery.get("offset");
            query += " OFFSET " + offset + ";";
        }

        try {
            Connection connection = DBConnect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Bank bank = new Bank(result.getString("ifsc"), result.getInt("bank_id"), result.getString("branch"),
                        result.getString("address"), result.getString("city"), result.getString("district"),
                        result.getString("state"));
                banks.add(bank);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return banks;
        Map<String, List> map = new HashMap();
        map.put("branches", banks);
        return map;
    }

    public Map<String, List> searchBranchService(@RequestParam Map<String, String> query) {

        String q = "", limit = "", offset = "";
        List<Bank> banks = new ArrayList<>();
        if (query.containsKey("q"))
            q = query.get("q");
        q = q.toLowerCase();
        String cond = "";
        if (q.chars().allMatch(Character::isDigit)) {
            cond = "bank_id,";
        }
        String SqlQuery = "Select * from branches where '" + q + "' in (Lower(ifsc)," + cond
                + "  Lower(branch), Lower(address), Lower(city), Lower(district), Lower(state)) order by ifsc ";
        if (query.containsKey("limit")) {
            limit = query.get("limit");
            SqlQuery += " LIMIT " + limit;
        }
        if (query.containsKey("offset")) {
            offset = query.get("offset");
            SqlQuery += " OFFSET " + offset + ";";
        }

        try {
            Connection connection = DBConnect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SqlQuery);

            while (result.next()) {
                Bank bank = new Bank(result.getString("ifsc"), result.getInt("bank_id"), result.getString("branch"),
                        result.getString("address"), result.getString("city"), result.getString("district"),
                        result.getString("state"));
                banks.add(bank);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return banks;
        Map<String, List> map = new HashMap();
        map.put("branches", banks);
        return map;
    }

}
