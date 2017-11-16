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
            String[] searchTerms = req.queryParams("searchtermscsv").split(",");
            int length = searchTerms.length;
            for (int i = 0; i < length; i++) {
                searchTerms[i] = searchTerms[i].trim();
            }
            Map<String, Object> model = new HashMap<>();

            // create search combos
            List<String> searchCombos = new LinkedList<>();
            int numCombos = 0;
            for (int i = 1; i <= length; i++) {
                numCombos +=
                        (MathHelper.factorial(length) /
                                (MathHelper.factorial(i) * MathHelper.factorial(length - i)));
            }

            for (int i = 1; i <= searchTerms.length; i++) {
                Permutation.getCombination(searchTerms, searchTerms.length, i, searchCombos);
            }
            model.put("searchCombos", searchCombos);

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }


}
