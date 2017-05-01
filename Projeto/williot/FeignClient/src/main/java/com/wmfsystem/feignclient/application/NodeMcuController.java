package com.wmfsystem.feignclient.application;

import com.wmfsystem.feignclient.domain.PinStat;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wmfsystem on 3/17/17.
 */

@RestController
@RequestMapping("/nodemcu")
public class NodeMcuController {

    @Autowired
    private NodeMcu nodeMcu;

    @RequestMapping(method = RequestMethod.GET)
    public PinStat change(@RequestParam(value = "pin", required = false) String pin, @RequestParam(value = "stat", required = false) String stat) {
        try {
            nodeMcu.change(pin, stat);
        } catch (Exception e) {
            System.out.println("--> Chamada realizada, porém cabeçalho não reconhecido");
        }
        return new PinStat(pin, stat);
    }

}
