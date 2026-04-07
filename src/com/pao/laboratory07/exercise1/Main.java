package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialStareComandaException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StareComanda initialState = StareComanda.valueOf(scanner.next());
        Order order = new Order(initialState);
        System.out.println("Initial order state: " + initialState);

        while (true) {
            OrderCommand orderCommand = OrderCommand.valueOf(scanner.next().toUpperCase());
            switch (orderCommand) {
                case NEXT -> {
                    try {
                        order.nextState();
                        System.out.println("Order state updated to: " + order.returnStateString());
                    } catch (OrderIsAlreadyFinalException e) {
                        System.out.println("Order is already in a final state.");
                    }
                }
                case CANCEL -> {
                    try {
                        order.cancel();
                        System.out.println("Order has been canceled.");
                    } catch (CannotCancelFinalOrderException e) {
                        System.out.println("Cannot cancel a final state order.");
                    }
                }
                case UNDO -> {
                    try {
                        order.undoState();
                        System.out.println("Order state reverted to: " + order.returnStateString());
                    } catch (CannotRevertInitialStareComandaException e) {
                        System.out.println("Cannot undo the initial order state.");
                    }
                }
                case QUIT -> {
                    System.out.println("User quit the program.");
                    return;
                }
            }
        }
    }
}
