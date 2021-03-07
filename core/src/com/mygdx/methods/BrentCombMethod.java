package com.mygdx.methods;

import java.util.Arrays;
import java.util.function.Function;

public class BrentCombMethod extends AbstractDrawableMethod {

    private final static double K = (3 - Math.sqrt(5)) / 2;

    public BrentCombMethod(Function<Double, Double> func) {
        super(func);
    }

    private static boolean allDistinct(Object... os) {
        return Arrays.stream(os).distinct().count() == os.length;
    }

    private static double sqr(double x) {
        return x * x;
    }

    public double findMin(double left, double right, double eps) {
        clear();
        // x - текущий минимум
        // w - второй минимум
        // v - предыдущее значение w (третий минимум, если w != v)
        // u - последняя вычисленная точка
        // d - смещение на предыдущем шаге
        // pd - предыдущее значение d
        // m - середина интервала
        double x = left + K * (right-left), w = x, v = x;
        double fx = callFun(x), fv = fx, fw = fx, fu;
        double d = 0, pd = d;
        double m, tol, u;
        tol = Math.abs(x) * eps + eps / 10;
        log(left + " " + right);
        while ((right - left) / 2 > eps) {
            addSegment(left, right);
            m = (left + right) / 2;
            // критерий останова
            if (Math.abs(x - m) + (right - left) / 2 <= 2 * tol) {
                break;
            }

            if (allDistinct(x, w, v) && allDistinct(fx, fw, fv)) {
                // вычисляем параболу
                double r = (x-w) * (fx - fv);
                double q = (x-v) * (fx - fw);
                double p = (x-v) * q - (x - w) * r;
                q = 2 * (q-r);
                if (q > 0) {
                    p = -p;
                } else {
                    q = -q;
                }

                double td = pd;
                pd = d;

                if ((Math.abs(p) >= Math.abs(q * td / 2)) || (p <= q * (left - x)) || (p >= q * (right - x))) {
                    // friendship ended with parabola
                    // now golden section is my best friend
                    pd = x >= m ? left - x : right - x;
                    d = K * pd;
                } else {
                    // оо повезло повезло
                    // обмазываемся параболой
                    d = p / q;
                    u = x + d;
                }
            } else {
                // золотое сечение без лишнего выпендрёжа
                pd = x >= m ? left - x : right - x;
                d = K * pd;
            }

            u = x + d;
            fu = callFun(u);

            if (fu <= fx) {
                // точка настоящий classic, я бы даже сказал pleasantly
                if (u >= x) {
                    left = x;
                } else {
                    right = x;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            } else {
                if (fu <= fw || w == x) {
                    // если точка лучше w (вторая лучшая)
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    // если точка лучше v (третья лучшая)
                    v = u;
                    fv = fu;
                }
                // точка помойка, но хотя бы лучше чем границы интервала
                if (u < x) {
                    left = u;
                } else {
                    right = u;
                }
            }

            log(left + " " + right);
        }


        return (right + left) / 2;
    }

}