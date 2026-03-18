package com.pao.laboratory03.bonus.enums;

public enum Priority {
    LOW(1,1.0),MEDIUM(2,1.5),HIGH(3,2.0),CRITICAL(4,3.0);

    Priority(int nivel, double multiplicator){
        this.level = nivel;
        this.multiplier = multiplicator;
    }

    public double calculateScore(int baseDays){
        return baseDays * multiplier;
    }

    int getLevel() { return level; }
    double getMultiplier() { return multiplier; }

    private int level;
    private double multiplier;
}
