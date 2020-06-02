package com.company.lab.calculation;

import java.util.ArrayList;
import java.util.List;

public class CalculationHelper {

    //    public static final double DISPERSION = 1.490;
    public static final double EXP = 0.005;

    public static Formula5Calculation getCalculationFor5Formula() {
        return new Formula5Calculation();
    }

    public static Formula7Calculation getCalculationFor7Formula() {
        return new Formula7Calculation();
    }

    public static Formula8Calculation getCalculationFor8Formula() {
        return new Formula8Calculation();
    }

    public static Formula9Calculation getCalculationFor9Formula() {
        return new Formula9Calculation();
    }

    public static class Formula5Calculation {

        List<Formula5Model> models;
        Formula5Model model;

        double dispersion;

        public List<Formula5Model> calculate(List<Double> xList, double exp) {
            models = new ArrayList<>();

            dispersion = 2 * calculateDispersion(xList);

            double XcPrev = calculateXc1(xList);

            for (int i = 1; true; i++) {
                double XcL = calculateXcL(i, xList, XcPrev, dispersion);

                double dif = Math.abs(XcL - XcPrev);

                XcPrev = XcL;

                if (dif <= exp) {
                    break;
                }
            }

            return models;
        }

        private double calculateDispersion(List<Double> xList) {
            double average = 0;

            for (Double x : xList) {
                average += x;
            }

            average /= xList.size();

            double sum = 0;

            for (Double x : xList) {
                sum += Math.pow(x - average, 2);
            }

            return sum / (xList.size() - 1);
        }

        private double calculateXc1(List<Double> xList) {
            double sum = 0;
            for (double xj : xList) {
                sum += xj;
            }

            Formula5Model model = new Formula5Model();
            model.result = sum / xList.size();
            model.iteration = 0;

            models.add(model);

            return sum / xList.size();
        }

        private double calculateXcL(int iteration, List<Double> xList, double XcPrev, double dispersion) {
            model = new Formula5Model();

            model.iteration = iteration;

            double XcL = 0;
            double numerator = calculateNumerator(xList, XcPrev, dispersion);
            double denominator = calculateDenominator(xList, XcPrev, dispersion);

            XcL = numerator / denominator;

            model.numeratorSum = numerator;
            model.denominatorSum = denominator;
            model.result = XcL;

            model.different = Math.abs(XcL - XcPrev);

            models.add(model);

            return XcL;
        }

        private double calculateNumerator(List<Double> xList, double xcPrev, double dispersion) {

            model.numeratorList = new ArrayList<>();

            double sum = 0;
            for (double x : xList) {
                double formula = calculateExpression(x, xList, xcPrev, dispersion);

                model.numeratorList.add(formula * x);

                sum += x * formula;
            }

            return sum;
        }

        private double calculateDenominator(List<Double> xList, double xcPrev, double dispersion) {

            model.denominatorList = new ArrayList<>();

            double sum = 0;
            for (double x : xList) {
                double formula = calculateExpression(x, xList, xcPrev, dispersion);

                model.denominatorList.add(formula);

                sum += formula;
            }

            return sum;
        }

        private double calculateExpression(double x, List<Double> xList, double xcPrev, double dispersion) {
            return Math.exp(-Math.pow(x - xcPrev, 2) * (xList.size() + 1) / (dispersion));
        }

        public double getDispersion() {
            return dispersion;
        }
    }

    public static class Formula7Calculation {

        List<Formula7Model> models;
        Formula7Model model;

        public List<Formula7Model> calculate(List<Double> xList, double exp) {
            models = new ArrayList<>();

            double XcPrev = calculateXc1(xList);

            for (int i = 1; true; i++) {
                double XcL = calculateXcL(i, xList, XcPrev);

                double dif = Math.abs(XcL - XcPrev);

                XcPrev = XcL;

                if (dif <= exp) {
                    break;
                }
            }

            return models;
        }

        private double calculateXc1(List<Double> xList) {
            double sum = 0;
            for (double xj : xList) {
                sum += xj;
            }

            Formula7Model model = new Formula7Model();
            model.result = sum / xList.size();
            model.iteration = 0;

            models.add(model);

            return sum / xList.size();
        }

        private double calculateXcL(int iteration, List<Double> xList, double XcPrev) {
            model = new Formula7Model();

            model.iteration = iteration;
            model.elementList = new ArrayList<>();

            double XcL = 0;

            for (double x : xList) {
                double denominator = calculateDenominator(x, xList, XcPrev);
                double func = x / denominator;

                model.elementList.add(func);

                XcL += func;
            }

            model.elementSum = XcL;
            model.result = XcL;

            model.different = Math.abs(XcL - XcPrev);

            models.add(model);

            return XcL;
        }

        private double calculateDenominator(double xj, List<Double> xList, double xcPrev) {

            double sum = 0;
            for (double x : xList) {
                double formula = calculateExpression(x, xcPrev);

                sum += 1 / formula;
            }

            return sum * calculateExpression(xj, xcPrev);
        }

        private double calculateExpression(double x, double xcPrev) {
            return Math.abs(xcPrev - x);
        }
    }

    public static class Formula8Calculation {

        List<Formula8Model> models;
        Formula8Model model;

        public List<Formula8Model> calculate(List<Double> xList, double exp) {
            models = new ArrayList<>();

            double XcPrev = calculateXc1(xList);

            for (int i = 1; true; i++) {
                double XcL = calculateXcL(i, xList, XcPrev);

                double dif = Math.abs(XcL - XcPrev);

                XcPrev = XcL;

                if (dif <= exp) {
                    break;
                }
            }

            return models;
        }

        private double calculateXc1(List<Double> xList) {
            double sum = 0;
            for (double xj : xList) {
                sum += xj;
            }

            Formula8Model model = new Formula8Model();
            model.result = sum / xList.size();
            model.iteration = 0;

            models.add(model);

            return sum / xList.size();
        }

        private double calculateXcL(int iteration, List<Double> xList, double XcPrev) {
            model = new Formula8Model();

            model.iteration = iteration;
            model.elementList = new ArrayList<>();

            double XcL = 0;

            for (double x : xList) {
                double denominator = calculateDenominator(x, xList, XcPrev);
                double func = x / denominator;

                model.elementList.add(func);

                XcL += func;
            }

            model.elementSum = XcL;
            model.result = XcL;

            model.different = Math.abs(XcL - XcPrev);

            models.add(model);

            return XcL;
        }

        private double calculateDenominator(double xj, List<Double> xList, double xcPrev) {

            double sum = 0;
            for (double x : xList) {
                double formula = calculateExpression(x, xcPrev);

                sum += 1 / formula;
            }

            return sum * calculateExpression(xj, xcPrev);
        }

        private double calculateExpression(double x, double xcPrev) {
            return Math.pow(xcPrev - x, 2);
        }
    }

    public static class Formula9Calculation {

        List<Formula9Model> models;
        Formula9Model model;

        public List<Formula9Model> calculate(List<Double> xList, double exp) {
            models = new ArrayList<>();

            double XcPrev = calculateXc1(xList);

            for (int i = 1; true; i++) {
                double XcL = calculateXcL(i, xList, XcPrev);

                double dif = Math.abs(XcL - XcPrev);

                XcPrev = XcL;

                if (dif <= exp) {
                    break;
                }
            }

            return models;
        }

        private double calculateXc1(List<Double> xList) {
            double sum = 0;
            for (double xj : xList) {
                sum += xj;
            }

            Formula9Model model = new Formula9Model();
            model.result = sum / xList.size();
            model.iteration = 0;

            models.add(model);

            return sum / xList.size();
        }

        private double calculateXcL(int iteration, List<Double> xList, double XcPrev) {
            model = new Formula9Model();

            model.iteration = iteration;
            model.elementList = new ArrayList<>();

            double XcL = 0;

            for (double x : xList) {
                double denominator = calculateDenominator(x, xList, XcPrev);
                double func = x / denominator;

                model.elementList.add(func);

                XcL += func;
            }

            model.elementSum = XcL;
            model.result = XcL;

            model.different = Math.abs(XcL - XcPrev);

            models.add(model);

            return XcL;
        }

        private double calculateDenominator(double xj, List<Double> xList, double xcPrev) {

            double sum = 0;
            for (double x : xList) {
                double formula = calculateExpression(x, xcPrev);

                sum += 1 / formula;
            }

            return sum * calculateExpression(xj, xcPrev);
        }

        private double calculateExpression(double x, double xcPrev) {
            return 1 + x * Math.pow(xcPrev - x, 2);
        }
    }
}
