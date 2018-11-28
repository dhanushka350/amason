package com.akvasoft.amason.common;

import javax.persistence.*;

@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;
    @Column(name = "productTitle")
    String productTitle;
    @Column(name = "monthly_revenue")
    String monthly_revenue;
    @Column(name = "avarage_price")
    String avarage_price;
    @Column(name = "best_selling_period")
    String best_selling_period;
    @Column(name = "review_increase")
    String review_increase;
    @Column(name = "sales_trend")
    String sales_trend;
    @Column(name = "idea_source")
    String idea_source;
    @Column(name = "monthly_sales")
    String monthly_sales;
    @Column(name = "sell_well")
    String sell_well;
    @Column(name = "pattern")
    String pattern;
    @Column(name = "warning")
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
