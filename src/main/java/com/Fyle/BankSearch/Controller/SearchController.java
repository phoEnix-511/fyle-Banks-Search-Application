package com.Fyle.BankSearch.Controller;

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
import com.Fyle.BankSearch.Service.SearchService;

@RestController
public class SearchController {
    private SearchService service = new SearchService();

    @RequestMapping(value = "/api/branches/autocomplete", method = RequestMethod.GET)
    public Map<String, List> controllerMethod(@RequestParam Map<String, String> customQuery) {
        return service.autocompleteService(customQuery);
    }

    @RequestMapping(value = "/api/branches", method = RequestMethod.GET)
    public Map<String, List> searchBranches(@RequestParam Map<String, String> query) {

        return service.searchBranchService(query);
    }

}
