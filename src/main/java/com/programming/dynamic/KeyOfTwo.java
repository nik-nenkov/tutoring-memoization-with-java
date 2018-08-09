package com.programming.dynamic;


public class KeyOfTwo {
    private Integer curPos;
    private Integer curRow;

    KeyOfTwo(int cp, int cr) {
        this.curPos = cp;
        this.curRow = cr;
    }

    @Override
    public boolean equals(Object object) {
        return getClass() == object.getClass()
                && (((KeyOfTwo) object).curPos == null
                && ((KeyOfTwo) object).curRow == null || ((KeyOfTwo) object).curPos == null
                && ((KeyOfTwo) object).curRow.equals(this.curRow) || ((KeyOfTwo) object).curPos.equals(this.curPos)
                && ((KeyOfTwo) object).curRow == null || ((KeyOfTwo) object).curPos.equals(this.curPos)
                && ((KeyOfTwo) object).curRow.equals(this.curRow));
    }

    @Override
    public int hashCode() {
        int hashCode = this.curPos == null ? 0 : this.curPos.hashCode();
        hashCode = hashCode + (this.curRow == null ? 0 : this.curRow.hashCode());
        return hashCode;
    }
}