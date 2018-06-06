package com.muxi.api.terminal.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muxi.api.terminal.models.entities.Terminal;
import com.muxi.api.terminal.models.feedback.ErrorFeedback;
import com.muxi.api.terminal.services.TerminalService;


@RestController
@RequestMapping("terminals")
public class TerminalController {

	private TerminalService terminalService;
	
	@Autowired
    public void setTerminalService(TerminalService terminalService) {
        this.terminalService = terminalService;
    }
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Terminal> createTerminal(@RequestBody String terminalStr){
		Terminal terminal = terminalService.createTerminalByString(terminalStr);
		ResponseEntity<Terminal> response = ResponseEntity.status(HttpStatus.ACCEPTED).body(terminal);
		
		return response;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public ResponseEntity<Terminal> createTerminal(@RequestBody Terminal terminal){
		Terminal savedTerminal = this.terminalService.saveOrUpdate(terminal);
		ResponseEntity<Terminal> response = ResponseEntity.status(HttpStatus.ACCEPTED).body(savedTerminal);

		return response;
	}
	
	@RequestMapping(value = "/{logic}", method = RequestMethod.GET)
	public ResponseEntity<Terminal> findTerminal(@PathVariable("logic") int logic){
		Terminal terminal = this.terminalService.findById(logic);
		ResponseEntity<Terminal> response = ResponseEntity.status(HttpStatus.ACCEPTED).body(terminal);

		return response;
	}
	
	@RequestMapping(value = "/{logic}", method = RequestMethod.PUT)
	public ResponseEntity<Terminal> updateTerminal(@PathVariable("logic") int logic, @RequestBody Terminal terminal){
		Terminal savedTerminal = this.terminalService.saveOrUpdate(terminal);
		ResponseEntity<Terminal> response = ResponseEntity.status(HttpStatus.ACCEPTED).body(savedTerminal);

		return response;
	}
	
	@RequestMapping(value = "/{logic}", method = RequestMethod.DELETE)
	public ResponseEntity<Terminal> deleteTerminal(@PathVariable("logic") int logic){		
		ResponseEntity<Terminal> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
		return response;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorFeedback> handleException(HttpServletRequest req, Exception ex){
		
		ErrorFeedback feedback = new ErrorFeedback();
		
		feedback.requestedUri = req.getRequestURI();
		feedback.error = ex.getMessage();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(feedback);
	}
	
}
