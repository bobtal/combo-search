package com.bobantalevski.combosearch;

import com.bobantalevski.combosearch.helpers.MathHelper;
import com.bobantalevski.combosearch.helpers.Permutation;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> {
            return new ModelAndView(null, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/search", (req, res) -> {
            // get query params
            String[] searchTerms = req.queryParams("searchtermscsv").split(",");
            String generalSearchTerm = req.queryParams("generalsearchterm");

            // trim each search term
            int length = searchTerms.length;
            for (int i = 0; i < length; i++) {
                searchTerms[i] = searchTerms[i].trim();
            }

            Map<String, Object> model = new HashMap<>();

            // create search combos list and add to it all search combos with two search terms and more
            List<String> searchCombos = new LinkedList<>();
            for (int i = 2; i <= searchTerms.length; i++) {
                Permutation.getCombination(searchTerms, searchTerms.length, i, searchCombos, generalSearchTerm);
            }
            model.put("searchCombos", searchCombos);

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }


}
