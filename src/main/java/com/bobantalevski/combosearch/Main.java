package com.bobantalevski.combosearch;

import com.bobantalevski.combosearch.helpers.MathHelper;
import com.bobantalevski.combosearch.helpers.Permutation;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.redirect;

public class Main {
    private static final String RESULTS_MODEL_KEY = "model";

    public static void main(String[] args) {

        get("/", (req, res) -> {
            Map<String, Object> model = captureModelResults(req);
            return new ModelAndView(model, "index.hbs");
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
            setModelResults(req, model);
            res.redirect("/");
            return null;
        });
    }

    private static void setModelResults(Request req, Map<String, Object> model) {
        req.session().attribute(RESULTS_MODEL_KEY, model);
    }

    private static Map<String, Object> getModelResults(Request req) {
        // if there's no session, return null
        if (req.session(false) == null) {
            return null;
        }
        // if there is a session, but it doesn't contain our model with results, return null
        if (!req.session().attributes().contains(RESULTS_MODEL_KEY)) {
            return null;
        }
        // otherwise return our model containing results
        return req.session().attribute(RESULTS_MODEL_KEY);
    }

    private static Map<String, Object> captureModelResults(Request req) {
        // get the model results
        Map<String, Object> modelResults = getModelResults(req);
        // if there was anything returned, it means the model existed and
        // we need to remove it from the session so it doesn't get included
        // on subsequent requests
        if (modelResults != null) {
            req.session().removeAttribute(RESULTS_MODEL_KEY);
        }
        return modelResults;
    }




}
