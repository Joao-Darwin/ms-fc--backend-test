package com.fcbackend.infrastructure.web.controller;

import com.fcbackend.infrastructure.web.controller.docs.ITweetController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tweets")
public class TweetController implements ITweetController {
}
