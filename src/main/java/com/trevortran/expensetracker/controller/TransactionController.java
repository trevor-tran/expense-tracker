package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.service.CategoryService;
import com.trevortran.expensetracker.service.TransactionService;
import com.trevortran.expensetracker.service.UserService;
import com.trevortran.expensetracker.util.OrderBy;
import com.trevortran.expensetracker.util.SortBy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ModelAndView getAllTransactions(@RequestParam(value = "sort", defaultValue = "date", required = false) String sortBy,
                                           @RequestParam(value = "order", defaultValue = "asc", required = false) String orderBy) {
        List<Transaction> transactions = transactionService.findAll();

        // convert string type to its corresponding enum
        OrderBy orderByEnum = OrderBy.stringToEnum(orderBy);
        SortBy sortByEnum = SortBy.stringToEnum(sortBy);

        // sort transactions
        // default: sort by date and ascending
        sortInPlace(transactions, sortByEnum, orderByEnum);

        ModelAndView modelAndView = new ModelAndView("expense");
        modelAndView.addObject("transactions", transactions);
        modelAndView.addObject("categories", categoryService.findAll());

        // pass sorting status to FE
        modelAndView.addObject("sort", sortBy.toLowerCase());
        modelAndView.addObject("order", orderBy.toLowerCase());

        return modelAndView;
    }

    @PostMapping("/")
    public RedirectView saveTransaction(@ModelAttribute Transaction transaction) {
        transactionService.save(transaction);

        return new RedirectView("/transaction");
    }

    @GetMapping("/analytic")
    public ModelAndView renderAnalytic() {
        ModelAndView modelAndView = new ModelAndView("analytic");
        modelAndView.addObject("transactions", transactionService.findAll());
        return modelAndView;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable("transactionId") UUID transactionId) {
        Optional<Transaction> transaction = transactionService.findById(transactionId);
        return transaction.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") UUID transactionId) {
        boolean exist = transactionService.existsById(transactionId);
        if (!exist) {
            return ResponseEntity.badRequest().build();
        }

        transactionService.delete(transactionId);

        return ResponseEntity.ok().build();
    }

    private void sortInPlace(List<Transaction> transactions, SortBy sortBy, OrderBy orderBy) {
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
