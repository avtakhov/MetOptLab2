package com.mygdx.methods;

public interface Method {

    /**
     * Принимает левую(left) и правую(right) границы поиска минимума и точность поиска eps
     * Возвращает найденный в зависимости от eps минимум
     */
    double findMin(double left, double right, double eps);
}
