package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.security.UserPrincipal;
import com.trevortran.expensetracker.service.CategoryService;
import com.trevortran.expensetracker.service.TransactionService;
import com.trevortran.expensetracker.util.OrderBy;
import com.trevortran.expensetracker.util.SortBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

/**
 * Controller handles all requests to "/transaction" path
 */
@Controller
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    @Autowired
    public TransactionController(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    /**
     * Retrieve all transactions/expenses which belong to the principal
     * The transactions/expenses are sorted in a particular order
     * @param userPrincipal authenticated user
     * @param sortBy which field to sort by
     * @param orderBy either 'asc' or 'desc'
     * @return ModelAndView
     */
    @GetMapping("")
    public ModelAndView showAllTransactions(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestParam(value = "sort", defaultValue = "date", required = false) String sortBy,
                                           @RequestParam(value = "order", defaultValue = "desc", required = false) String orderBy) {

        UUID userId = userPrincipal.getId();

        log.info("Getting user's transaction, performed by authenticated userId: " + userId);

        // todo: ideally should call getTransactions() but currently it throws lazy loading error
        List<Transaction> transactions = transactionService.findAllByUserId(userId);
        transactions = transactions != null ? transactions : new ArrayList<>();
        OrderBy orderByEnum = OrderBy.stringToEnum(orderBy);
        SortBy sortByEnum = SortBy.stringToEnum(sortBy);

        // sort transactions
        // default: sort by date and descending if none specified
        sortInPlace(transactions, sortByEnum, orderByEnum);

        log.info("Populating user's transaction to template");

        ModelAndView modelAndView = new ModelAndView("expense");
        modelAndView.addObject("transactions", transactions);
        modelAndView.addObject("categories", categoryService.findAll());

        // pass sorting status to FE
        modelAndView.addObject("sort", sortBy.toLowerCase());
        modelAndView.addObject("order", orderBy.toLowerCase());

        return modelAndView;
    }

    /**
     * Handle adding/editing a transaction
     * @param userPrincipal authenticated user
     * @param transaction Transaction
     * @return RedirectView
     */
    @PostMapping("")
    public RedirectView saveTransaction(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @ModelAttribute Transaction transaction) {

        UUID userId = userPrincipal.getId();

        transaction.setUserId(userId);
        log.info("Saving user's transaction, performed by userId: " + userId);
        transactionService.save(transaction);
        log.info("A transaction saved, performed by userId: " + userId);

        return new RedirectView("/transaction");
    }

    /**
     * display Analytic page with injected transactions which belong to authenticated user
     * @param userPrincipal authenticated user
     * @return ModelAndView
     */
    @GetMapping("/analytic")
    public ModelAndView showAnalytic(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UUID userId = userPrincipal.getId();

        log.info("showing user's analytic, performed by userId: " + userId);

        List<Transaction> transactions = transactionService.findAllByUserId(userId);

        ModelAndView modelAndView = new ModelAndView("analytic");
        modelAndView.addObject("transactions", transactions);
        return modelAndView;
    }

    /**
     * Retrieve a transaction/expense
     * @param transactionId the Id of the transaction/expense
     * @return Transaction
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @PathVariable("transactionId") UUID transactionId) {
        UUID userId = userPrincipal.getId();

        log.info("Getting transaction, performed by userId: " + userId);

        Optional<Transaction> transaction = transactionService.findById(transactionId);

        return transaction.map(response -> {
            //verify this is his or her transaction
            if (response.getUserId().equals(userId)) {
                return ResponseEntity.ok(response);
            } else {
                log.warn("User is trying to retrieve a transaction that does not belong to them");
                return ResponseEntity.badRequest().build();
            }
        }).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * delete a transaction/expense
     * @param transactionId the Id of the transaction/expense deleted
     * @return OK status code if success, otherwise BadRequest status code
     */
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction (@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @PathVariable("transactionId") UUID transactionId) {
        UUID userId = userPrincipal.getId();

        log.info("Deleting transaction, performed by userId: " + userId);

        Optional<Transaction> transaction = transactionService.findById(transactionId);

        return transaction.map(response -> {
            //verify this is his or her transaction
            if (response.getUserId().equals(userId)) {
                transactionService.delete(transactionId);
                return ResponseEntity.ok(response);
            } else {
                log.warn("User is trying to delete a transaction that does not belong to them");
                return ResponseEntity.badRequest().build();
            }
        }).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Sort the list of transactions in place
     * @param transactions list of transactions
     * @param sortBy Enum, specifying which field to sort on
     * @param orderBy Enum, either "asc" or "desc" ordering
     */
    private void sortInPlace(List<Transaction> transactions, SortBy sortBy, OrderBy orderBy) {
        if (transactions == null || transactions.isEmpty()) {
            return;
        }

        if (sortBy == SortBy.DESCRIPTION) {
            if (orderBy == OrderBy.ASC) {
                transactions.sort(Comparator.comparing(Transaction::getDescription));
            } else {
                transactions.sort(Comparator.comparing(Transaction::getDescription).reversed());
            }
        } else if (sortBy == SortBy.CATEGORY) {
            if (orderBy == OrderBy.ASC) {
                transactions.sort(Comparator.comparing(Transaction::getCategory));
            } else {
                transactions.sort(Comparator.comparing(Transaction::getCategory).reversed());
            }
        } else if (sortBy == SortBy.AMOUNT) {
            if (orderBy == OrderBy.ASC) {
                transactions.sort(Comparator.comparing(Transaction::getAmount));
            } else {
                transactions.sort(Comparator.comparing(Transaction::getAmount).reversed());
            }
        } else {
            if (orderBy == OrderBy.ASC) {
                transactions.sort(Comparator.comparing(Transaction::getDate));
            } else {
                transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
            }
        }
    }
}
