package com.redhat.training;

import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExpenseResource {

    public ExpenseService expenseService;


    public Set<Expense> list() {
        return expenseService.list();
    }


    public Expense create(Expense expense) {
        return expenseService.create(expense);
    }


    public Set<Expense> delete(UUID uuid) {
        if (!expenseService.delete(uuid)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return expenseService.list();
    }


    public void update(Expense expense) {
        expenseService.update(expense);
    }
    // Configure method Path for url /expenses/query
    // Add QueryPAram named from type int
    // Add QueryParam named to type int
    // Add QueryParam named orderBy type List<String>
    public Response getUsers(int from, int to, List<String> orderBy) {

            return Response
                .status(200)
                .entity("getUsers is called, from : " + from + ", to : " + to
                    +  ", orderBy" + orderBy.toString()).build();
    }
}
