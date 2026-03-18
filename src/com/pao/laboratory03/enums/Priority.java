package com.pao.laboratory03.enums;

public enum Priority {
    LOW(1, "Verde") {
        @Override
        public String getEmoji() {
            return "\uD83D\uDFE2";
        }
    },
    MEDIUM(2, "Galben") {
        @Override
        public String getEmoji() {
            return "\uD83D\uDFE1";
        }
    },
    HIGH(3, "Portocaliu") {
        @Override
        public String getEmoji() {
            return "\uD83D\uDFE0";
        }
    },
    CRITICAL(4, "Red") {
        @Override
        public String getEmoji() {
            return "\uD83D\uDD34";
        }
    };
    Priority(int level, String color){
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return level;
    }
    public String getColor() {
        return color;
    }

    public abstract String getEmoji();

    private int level;
    private String color;

}
