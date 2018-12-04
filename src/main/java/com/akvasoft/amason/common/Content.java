package com.akvasoft.amason.common;

import javax.persistence.*;

@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    int id;

    @Column(name = "PRODUCT_TITLE")
    String productTitle;

    @Column(name = "MONTHLY_REVENUE")
    String monthly_revenue;

    @Column(name = "AVERAGE_PRICE_OVER_90_DAYS")
    String avarage_price;

    @Column(name = "BEST_SELLING_PERIOD")
    String best_selling_period;

    @Column(name = "RATE_OF_REVIEW_INCREASE")
    String review_increase;

    @Column(name = "ANNUAL_SALES_TREND")
    String sales_trend;

    @Column(name = "PRODUCT_IDEA_SOURCE")
    String idea_source;

    @Column(name = "POSSIBLE_MONTHLY_SALES")
    String monthly_sales;

    @Column(name = "REVIEWS_NEED_TO_SELL_WELL")
    String sell_well;

    @Column(name = "SALES_PATTERN_ANALYSIS")
    String pattern;

    @Column(name = "TIPS_WARNINGS_AND_ALERT")
    String warning;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMonthly_revenue() {
        return monthly_revenue;
    }

    public void setMonthly_revenue(String monthly_revenue) {
        this.monthly_revenue = monthly_revenue;
    }

    public String getAvarage_price() {
        return avarage_price;
    }

    public void setAvarage_price(String avarage_price) {
        this.avarage_price = avarage_price;
    }

    public String getBest_selling_period() {
        return best_selling_period;
    }

    public void setBest_selling_period(String best_selling_period) {
        this.best_selling_period = best_selling_period;
    }

    public String getReview_increase() {
        return review_increase;
    }

    public void setReview_increase(String review_increase) {
        this.review_increase = review_increase;
    }

    public String getSales_trend() {
        return sales_trend;
    }

    public void setSales_trend(String sales_trend) {
        this.sales_trend = sales_trend;
    }

    public String getIdea_source() {
        return idea_source;
    }

    public void setIdea_source(String idea_source) {
        this.idea_source = idea_source;
    }

    public String getMonthly_sales() {
        return monthly_sales;
    }

    public void setMonthly_sales(String monthly_sales) {
        this.monthly_sales = monthly_sales;
    }

    public String getSell_well() {
        return sell_well;
    }

    public void setSell_well(String sell_well) {
        this.sell_well = sell_well;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
