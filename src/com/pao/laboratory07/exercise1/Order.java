package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.StareComanda;
import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialStareComandaException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public Order(StareComanda stare) {
        this.stare = stare;
        stariAnterioare = new ArrayList<>();
    }
    StareComanda stare;
    List<StareComanda> stariAnterioare;

    public void nextState(){
        if (stare == StareComanda.DELIVERED) throw new OrderIsAlreadyFinalException("Comanda finala");
        if (stare != StareComanda.CANCELLED) {
            stariAnterioare.add(stare);
            stare = stare.next();
        }
    }

    public void cancel(){
        if (stare == StareComanda.DELIVERED) throw new CannotCancelFinalOrderException("Comanda finala");
        stariAnterioare.add(stare);
        stare = StareComanda.CANCELLED;
    }

    public void undoState(){
        if (stare == StareComanda.PLACED) throw new CannotRevertInitialStareComandaException("Nu se poate reveni peste starea initiala");
        stariAnterioare.remove(stare);
        stare = stariAnterioare.get(stariAnterioare.size()-1);
    }

    public String returnStateString(){
        return stare.getName();
    }
}
