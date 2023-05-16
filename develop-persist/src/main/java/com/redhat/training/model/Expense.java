package com.redhat.training.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Type;

public class Expense {

    public enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    @Type(type = "uuid-char")
    @NotNull
    public UUID uuid;
    public String name;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime creationDate;
    public PaymentMethod paymentMethod;
    public BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonbTransient
    @JoinColumn(name = "associate_id", insertable = false, updatable = false)
    public Associate associate;

    @Column(name = "associate_id")
    public Long associateId;

    public Expense() { }

    public Expense(UUID uuid, String name, LocalDateTime creationDate,
            PaymentMethod paymentMethod, String amount, Associate associate) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal(amount);
        this.associate = associate;
        // TODO: Add associateId association
        this.associateId = associate.id;
    }

    public Expense(String name, PaymentMethod paymentMethod, String amount, Associate associate) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount, associate);
    }

    @JsonbCreator
    public static Expense of(String name, PaymentMethod paymentMethod, String amount, Long associateId) {
       return Associate.<Associate>findByIdOptional(associateId)
          .map(associate -> new Expense(name, paymentMethod, amount, associate))
          .orElseThrow(RuntimeException::new);
    }

    public static void update( final Expense expense ) {
        Optional<Expense> previous = Expense.findByIdOptional( expense.id );
        previous.ifPresentOrElse(( update ) -> {
            update.uuid = expense.uuid;
            update.name = expense.name;
            update.amount = expense.amount;
            update.paymentMethod = expense.paymentMethod;
            update.persist();
        }, () -> {
            throw new WebApplicationException( Response.Status.NOT_FOUND );
        });
}
}
