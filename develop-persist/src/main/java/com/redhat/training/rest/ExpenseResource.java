package com.redhat.training.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

import com.redhat.training.model.Expense;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {

    @GET
    // TODO 1: Implement with a call to "listAll()" of Expense entity.
    // TODO 2: Add pagination and sort by "amount" and "associateId".
    public List<Expense> list() {
        return Expense.listAll();
    }

    @POST
    @Transactional
    public Expense create(final Expense expense) {
        Expense newExpense = Expense.of(expense.name, expense.paymentMethod,
                expense.amount.toString(), expense.associateId);
        newExpense.persist();

        return newExpense;
    }

    @DELETE
    @Path("{uuid}")
    @Transactional
    public void delete(@PathParam("uuid") final UUID uuid) {
        long numExpensesDeleted = Expense.delete("uuid", uuid);

        if (numExpensesDeleted == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Transactional
    public void update(final Expense expense) {
        try {
            Expense.update(expense);
        } catch (RuntimeException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
