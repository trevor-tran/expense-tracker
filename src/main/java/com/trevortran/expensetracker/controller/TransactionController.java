package com.trevortran.expensetracker.controller;

import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.service.CategoryService;
import com.trevortran.expensetracker.service.TransactionService;
import com.trevortran.expensetracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ModelAndView getAllTransactions() {
        ModelAndView modelAndView = new ModelAndView("expense");
        modelAndView.addObject("transactions", transactionService.findAll());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView saveTransaction(@ModelAttribute Transaction transaction) {
        Transaction persistedTransaction = transactionService.save(transaction);
        ModelAndView modelAndView = new ModelAndView("expense");
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("transactions", transactionService.findAll());
        System.out.println(persistedTransaction);
        return modelAndView;
    }

    @GetMapping("/analytic")
    public ModelAndView renderAnalytic() {
        ModelAndView modelAndView = new ModelAndView("analytic");
        modelAndView.addObject("transactions", transactionService.findAll());
        return modelAndView;
    }

    @PutMapping("/{userId}/transaction/{transactionId}")
    public String editTransaction(@PathVariable("transactionId") UUID transactionId,  @RequestBody Transaction transaction) {
        if (!transaction.getId().equals(transactionId)) {
            return "bad request";
        }

        boolean exist = transactionService.existsById(transaction.getId());
        if (!exist) {
            return "bad request";
        }
        transactionService.update(transaction);
        return "good";
    }

    @DeleteMapping("/{userId}/transaction/{transactionId}")
    public String deleteTransaction(@PathVariable("transactionId") UUID transactionId) {
        boolean exist = transactionService.existsById(transactionId);
        if (!exist) {
            return "bad request";
        }

        transactionService.delete(transactionId);
        return "success";
    }
}
