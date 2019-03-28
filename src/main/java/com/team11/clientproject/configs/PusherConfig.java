package com.team11.clientproject.configs;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PusherConfig {
    @Value("${pusher.appid}")
    private String appid;
    @Value("${pusher.key}")
    private String key;
    @Value("${pusher.cluster}")
    private String cluster;
    @Value("${pusher.secret}")
    private String secret;

    // ref: pusher documentation on account creation
    // accessed on 1/12/2018
    @Bean
    public Pusher pusher() {
        Pusher pusher = new Pusher(appid, key, secret);
        pusher.setCluster(cluster);
        pusher.setEncrypted(true);
        return pusher;
    }
}
