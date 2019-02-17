package org.dodo.violet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {


    Logger logger;
    {
        logger = LoggerFactory.getLogger(CommonService.class);
    }


}
