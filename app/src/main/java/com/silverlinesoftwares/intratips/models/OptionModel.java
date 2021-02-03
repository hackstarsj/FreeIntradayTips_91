package com.silverlinesoftwares.intratips.models;

public class OptionModel {

    private String id;
    private String name;
    private String target1;
    private String target2;
    private String target3;
    private String buy1;
    private String buy2;
    private String buy3;
    private String achieved1;
    private String achieved2;
    private String achieved3;
    private String stop_loss;
    private String stop_loss_text;
    private String stop_loss_end;
    private String datetime;
    private String cmp_datetime;
    private String cmp_price;
    private String buy_price;
    private String created_at;
    private String notification_message;

    public String getNotification_message() {
        return notification_message;
    }

    public void setNotification_message(String notification_message) {
        this.notification_message = notification_message;
    }

    public String buy_text;

    public String getBuy_text() {
        return buy_text;
    }

    public void setBuy_text(String buy_text) {
        buy_text = buy_text;
    }

    public OptionModel() {
    }

    public String getStop_loss_end() {
        return stop_loss_end;
    }

    public void setStop_loss_end(String stop_loss_end) {
        this.stop_loss_end = stop_loss_end;
    }

    public OptionModel(String id, String name, String target1, String target2, String target3, String buy1, String buy2, String buy3, String achieved1, String achieved2, String achieved3, String stop_loss, String stop_loss_text, String stop_loss_end, String datetime, String cmp_datetime, String cmp_price, String buy_price, String created_at, String buy_text,String notification_message) {
        this.id = id;
        this.name = name;
        this.target1 = target1;
        this.target2 = target2;
        this.target3 = target3;
        this.buy1 = buy1;
        this.buy2 = buy2;
        this.buy3 = buy3;
        this.achieved1 = achieved1;
        this.achieved2 = achieved2;
        this.achieved3 = achieved3;
        this.stop_loss = stop_loss;
        this.stop_loss_text = stop_loss_text;
        this.stop_loss_end = stop_loss_end;
        this.datetime = datetime;
        this.cmp_datetime = cmp_datetime;
        this.cmp_price = cmp_price;
        this.buy_price = buy_price;
        this.created_at = created_at;
        this.buy_text = buy_text;
        this.notification_message=notification_message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget1() {
        return target1;
    }

    public void setTarget1(String target1) {
        this.target1 = target1;
    }

    public String getTarget2() {
        return target2;
    }

    public void setTarget2(String target2) {
        this.target2 = target2;
    }

    public String getTarget3() {
        return target3;
    }

    public void setTarget3(String target3) {
        this.target3 = target3;
    }

    public String getBuy1() {
        return buy1;
    }

    public void setBuy1(String buy1) {
        this.buy1 = buy1;
    }

    public String getBuy2() {
        return buy2;
    }

    public void setBuy2(String buy2) {
        this.buy2 = buy2;
    }

    public String getBuy3() {
        return buy3;
    }

    public void setBuy3(String buy3) {
        this.buy3 = buy3;
    }

    public String getAchieved1() {
        return achieved1;
    }

    public void setAchieved1(String achieved1) {
        this.achieved1 = achieved1;
    }

    public String getAchieved2() {
        return achieved2;
    }

    public void setAchieved2(String achieved2) {
        this.achieved2 = achieved2;
    }

    public String getAchieved3() {
        return achieved3;
    }

    public void setAchieved3(String achieved3) {
        this.achieved3 = achieved3;
    }

    public String getStop_loss() {
        return stop_loss;
    }

    public void setStop_loss(String stop_loss) {
        this.stop_loss = stop_loss;
    }

    public String getStop_loss_text() {
        return stop_loss_text;
    }

    public void setStop_loss_text(String stop_loss_text) {
        this.stop_loss_text = stop_loss_text;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCmp_datetime() {
        return cmp_datetime;
    }

    public void setCmp_datetime(String cmp_datetime) {
        this.cmp_datetime = cmp_datetime;
    }

    public String getCmp_price() {
        return cmp_price;
    }

    public void setCmp_price(String cmp_price) {
        this.cmp_price = cmp_price;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
