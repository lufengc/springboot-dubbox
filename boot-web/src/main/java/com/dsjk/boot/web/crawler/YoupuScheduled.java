package com.dsjk.boot.web.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * @author fengcheng
 * @version 2017/8/21
 */
@Component
public class YoupuScheduled {

    private final YoupuPipeline ratePipeline;

    @Autowired
    public YoupuScheduled(YoupuPipeline ratePipeline) {
        this.ratePipeline = ratePipeline;
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void BankOfChinaScheduled() {
        Spider.create(new YoupuProcessor())
                .addUrl("http://www.youpu.cn/Destination")
                .addPipeline(ratePipeline)
                .thread(5)
                .run();
    }
}
