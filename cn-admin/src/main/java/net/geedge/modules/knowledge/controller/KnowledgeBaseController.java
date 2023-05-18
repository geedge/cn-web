package net.geedge.modules.knowledge.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import net.geedge.modules.knowledge.service.KnowledgeBaseService;

@RestController
@RequestMapping("/v1/knowledgeBase")
public class KnowledgeBaseController {
	@Autowired
	private KnowledgeBaseService knowledgeBaseService;

}
