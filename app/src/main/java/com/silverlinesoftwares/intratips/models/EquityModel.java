package com.silverlinesoftwares.intratips.models;

public class EquityModel {

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
    private String pr_close;
    private String low;
    private String high;
    private String symbol;
    private String opens;
    private String oldprice;

    private String OldPrice;
    private String pre_mkt_change;
    private String realtime_change;
    private String exchange_external_id;
    private String pre_mkt_price;
    private String pre_mkt_chg_percent;
    private String volume;
    private String market_cap;
    private String price;
    private String year_low;
    private String avg_3m_volume;
    private String day_low;
    private String issuer_name_lang;
    private String issuer_name;
    private String open;
    private String realtime_time;
    private String change;
    private String year_high;
    private String realtime_chg_percent;
    private String pe_ratio;
    private String time;
    private String eps_curr_year;
    private String realtime_price;
    private String currency;
    private String data_type;
    private String exchange;
    private String chg_percent;
    private String prev_close;
    private String day_high;
    private String dividend_rate;
    private String exchange_id;
    private String dividend_yield;
    private String ts;
    private String realtime_ts;
    private String notification_message;

    public String getNotification_message() {
        return notification_message;
    }

    public void setNotification_message(String notification_message) {
        this.notification_message = notification_message;
    }

    private boolean is_Open=false;

    public boolean isIs_Open() {
        return is_Open;
    }

    public void setIs_Open(boolean is_Open) {
        this.is_Open = is_Open;
    }

    public String getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(String oldPrice) {
        OldPrice = oldPrice;
    }

    public String getPre_mkt_change() {
        return pre_mkt_change;
    }

    public void setPre_mkt_change(String pre_mkt_change) {
        this.pre_mkt_change = pre_mkt_change;
    }

    public String getRealtime_change() {
        return realtime_change;
    }

    public void setRealtime_change(String realtime_change) {
        this.realtime_change = realtime_change;
    }

    public String getExchange_external_id() {
        return exchange_external_id;
    }

    public void setExchange_external_id(String exchange_external_id) {
        this.exchange_external_id = exchange_external_id;
    }

    public String getPre_mkt_price() {
        return pre_mkt_price;
    }

    public void setPre_mkt_price(String pre_mkt_price) {
        this.pre_mkt_price = pre_mkt_price;
    }

    public String getPre_mkt_chg_percent() {
        return pre_mkt_chg_percent;
    }

    public void setPre_mkt_chg_percent(String pre_mkt_chg_percent) {
        this.pre_mkt_chg_percent = pre_mkt_chg_percent;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(String market_cap) {
        this.market_cap = market_cap;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYear_low() {
        return year_low;
    }

    public void setYear_low(String year_low) {
        this.year_low = year_low;
    }

    public String getAvg_3m_volume() {
        return avg_3m_volume;
    }

    public void setAvg_3m_volume(String avg_3m_volume) {
        this.avg_3m_volume = avg_3m_volume;
    }

    public String getDay_low() {
        return day_low;
    }

    public void setDay_low(String day_low) {
        this.day_low = day_low;
    }

    public String getIssuer_name_lang() {
        return issuer_name_lang;
    }

    public void setIssuer_name_lang(String issuer_name_lang) {
        this.issuer_name_lang = issuer_name_lang;
    }

    public String getIssuer_name() {
        return issuer_name;
    }

    public void setIssuer_name(String issuer_name) {
        this.issuer_name = issuer_name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getRealtime_time() {
        return realtime_time;
    }

    public void setRealtime_time(String realtime_time) {
        this.realtime_time = realtime_time;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getYear_high() {
        return year_high;
    }

    public void setYear_high(String year_high) {
        this.year_high = year_high;
    }

    public String getRealtime_chg_percent() {
        return realtime_chg_percent;
    }

    public void setRealtime_chg_percent(String realtime_chg_percent) {
        this.realtime_chg_percent = realtime_chg_percent;
    }

    public String getPe_ratio() {
        return pe_ratio;
    }

    public void setPe_ratio(String pe_ratio) {
        this.pe_ratio = pe_ratio;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEps_curr_year() {
        return eps_curr_year;
    }

    public void setEps_curr_year(String eps_curr_year) {
        this.eps_curr_year = eps_curr_year;
    }

    public String getRealtime_price() {
        return realtime_price;
    }

    public void setRealtime_price(String realtime_price) {
        this.realtime_price = realtime_price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getChg_percent() {
        return chg_percent;
    }

    public void setChg_percent(String chg_percent) {
        this.chg_percent = chg_percent;
    }

    public String getPrev_close() {
        return prev_close;
    }

    public void setPrev_close(String prev_close) {
        this.prev_close = prev_close;
    }

    public String getDay_high() {
        return day_high;
    }

    public void setDay_high(String day_high) {
        this.day_high = day_high;
    }

    public String getDividend_rate() {
        return dividend_rate;
    }

    public void setDividend_rate(String dividend_rate) {
        this.dividend_rate = dividend_rate;
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getDividend_yield() {
        return dividend_yield;
    }

    public void setDividend_yield(String dividend_yield) {
        this.dividend_yield = dividend_yield;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getRealtime_ts() {
        return realtime_ts;
    }

    public void setRealtime_ts(String realtime_ts) {
        this.realtime_ts = realtime_ts;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getStop_loss_end() {
        return stop_loss_end;
    }

    public void setStop_loss_end(String stop_loss_end) {
        this.stop_loss_end = stop_loss_end;
    }

    public String getPr_close() {
        return pr_close;
    }

    public void setPr_close(String pr_close) {
        this.pr_close = pr_close;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String buy_text;

    public String getBuy_text() {
        return buy_text;
    }

    public void setBuy_text(String buy_text) {
        this.buy_text = buy_text;
    }

    public EquityModel() {
    }

    public EquityModel(String id, String name, String target1, String target2, String target3, String buy1, String buy2, String buy3, String achieved1, String achieved2, String achieved3, String stop_loss, String stop_loss_text, String stop_loss_end, String datetime, String cmp_datetime, String cmp_price, String buy_price, String created_at, String pr_close, String low, String high, String buy_text,String notification_message) {
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
        this.pr_close = pr_close;
        this.low = low;
        this.high = high;
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
